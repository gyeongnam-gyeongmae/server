package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemCommentRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.domain.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class AuctionItemCommentServiceImpl implements AuctionItemCommentService {

    private final AuctionItemCommentRepository auctionItemCommentRepository;
    private final MemberRepository memberRepository;
    private final AuctionItemRepository auctionItemRepository;

    @Transactional
    public void createAuctionItemComment(AuctionItemCommentRequest auctionItemCommentRequest, Long id, Long memberId) {
        Member member = (Member) this.memberRepository.findById(memberId).orElseThrow(() -> {
            return new RuntimeException("유저를 찾을수 없습니다");
        });
        AuctionItem auctionItem = (AuctionItem) this.auctionItemRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("경매품을 찾을수 없습니다");
        });
        Comment parentComment = null;
        Long parentCommentId = auctionItemCommentRequest.getParentCommentId();
        if (parentCommentId >= 1L) {
            parentComment = (Comment) this.auctionItemCommentRepository.findById(parentCommentId).orElseThrow(() -> {
                return new RuntimeException("부모 댓글을 찾을수 없습니다");
            });
        }

        Comment comment = Comment.builder().content(auctionItemCommentRequest.getContent()).member(member).auctionItem(auctionItem).parent(parentComment).build();
        this.auctionItemCommentRepository.save(comment);
        auctionItem.setComment_count(auctionItem.getComment_count() + 1L);
        this.auctionItemRepository.save(auctionItem);
    }

    @Transactional(readOnly = true)
    public List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id) {
        List<Comment> commentEntityList = this.auctionItemCommentRepository.findByAuctionItemCommentByAuctionId(id);
        return commentEntityList.stream().map(AuctionItemCommentParentDto::of).collect(Collectors.toList());
    }

    @Transactional
    public void updateAuctionItemComment(AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest) {
        Comment comment = (Comment) this.auctionItemCommentRepository.findById(auctionItemCommentUpdateRequest.getCommentId()).orElseThrow(() -> {
            return new RuntimeException("댓글을 찾을수 없습니다");
        });
        if (!Objects.equals(comment.getMember().getId(), auctionItemCommentUpdateRequest.getUserId())) {
            throw new RuntimeException("댓글 작성자가 아닙니다");
        } else {
            comment.setContent(auctionItemCommentUpdateRequest.getContent());
            this.auctionItemCommentRepository.save(comment);
        }
    }

    @Transactional
    public void deleteAuctionItemComment(AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest) {
        Comment comment = (Comment) this.auctionItemCommentRepository.findById(auctionItemCommentDeleteRequest.getCommentId()).orElseThrow(() -> {
            return new RuntimeException("댓글을 찾을수 없습니다");
        });
        if (!Objects.equals(comment.getMember().getId(), auctionItemCommentDeleteRequest.getUserId())) {
            throw new RuntimeException("댓글 작성자가 아닙니다");
        } else {
            comment.setRemoved(true);
            this.auctionItemCommentRepository.save(comment);
        }
    }

}
