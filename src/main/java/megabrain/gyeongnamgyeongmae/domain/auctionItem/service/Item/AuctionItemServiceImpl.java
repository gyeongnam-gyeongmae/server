package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemLikeRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.domain.category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.domain.repository.MemberRepository;
import megabrain.gyeongnamgyeongmae.domain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

    private final AuctionItemRepository auctionItemRepository;
    private final CategoryService categoryService;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final AuctionItemLikeRepository auctionItemLikeRepository;

    @Override
    @Transactional
    public void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest) {
        checkClosedTime(createAuctionItemRequest.getClosedTime());
        Member memberEntity = memberRepository.findById(createAuctionItemRequest.getMember()).orElseThrow(RuntimeException::new);
        Category categoryEntity =
                categoryService.findCategoryByName(createAuctionItemRequest.getCategory());
        AuctionItem auctionItem = createAuctionItemRequest.toEntity();
        auctionItem.setMember(memberEntity);
        auctionItem.setCategory(categoryEntity);
        auctionItemRepository.save(auctionItem);
    }

    @Override
    @Transactional
    public AuctionItemResponse findAuctionItemById(Long id) {
        AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
        auctionItem.checkShowAuctionItem(auctionItem);
        updateAuctionItemViewCount(auctionItem);
        return AuctionItemResponse.of(auctionItem);
    }

    @Override
    public void updateAuctionItemViewCount(AuctionItem auctionItem) {
        auctionItem.setView_count(auctionItem.getView_count() + 1);
        auctionItemRepository.save(auctionItem);
    }

    @Override
    @Transactional
    public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest, Long id) {
        checkClosedTime(upDateAuctionItemRequest.getClosedTime());
        Category categoryEntity =
                categoryService.findCategoryByName(upDateAuctionItemRequest.getCategory());
        AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);

        if (!(Objects.equals(auctionItem.getMember().getId(), upDateAuctionItemRequest.getMemberId()))) {
            throw new RuntimeException("잘못된 회원입니다 ");
        }

        auctionItem.checkShowAuctionItem(auctionItem);
        auctionItem.updateAuctionItem(upDateAuctionItemRequest);
        auctionItem.setCategory(categoryEntity);
        auctionItemRepository.save(auctionItem);
    }

    @Override
    public void checkClosedTime(LocalDateTime closedTime) {
        LocalDateTime now = LocalDateTime.now();
        if (closedTime.isBefore(now.plusHours(24))) {
            throw new RuntimeException("경매 종료 시간은 현재 시간보다 24시간 이후여야 합니다.");
        }
    }

    @Override
    @Transactional
    public void deleteAuctionItemById(Long id) {
        AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
        auctionItem.removeAuctionItem(auctionItem);
        auctionItemRepository.save(auctionItem);
    }

    @Override
    @Transactional
    public void likeAuctionItemById(Long id, AuctionItemLikeRequest auctionItemLikeRequest) {
        AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
        Member member = memberRepository.findById(auctionItemLikeRequest.getMemberId()).orElseThrow(RuntimeException::new);

        AuctionItemLikePK auctionItemLikePK = new AuctionItemLikePK();
        auctionItemLikePK.setAuctionItemId(id);
        auctionItemLikePK.setMemberId(auctionItemLikeRequest.getMemberId());

        AuctionItemLike auctionItemLike = auctionItemLikeRepository.findById(auctionItemLikePK).orElse(null);

        if(auctionItemLike != null ){
            auctionItemLikeRepository.delete(auctionItemLike);
            auctionItem.setLike_count(auctionItem.getLike_count() - 1);
            auctionItemRepository.save(auctionItem);
        }
        else {
            auctionItemLike = AuctionItemLike.builder().id(auctionItemLikePK).auctionItem(auctionItem).member(member).build();
            auctionItemLikeRepository.save(auctionItemLike);
            auctionItem.setLike_count(auctionItem.getLike_count() + 1);
            auctionItemRepository.save(auctionItem);
        }
    }
}
