package megabrain.gyeongnamgyeongmae.domain.user.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService implements UserProfileServiceInterface{

    private final AuctionItemService auctionItemService;

    @Override
    public List<AuctionItemLike> findLikedAuctionItemIdsByUserId(Long userId) {
        return auctionItemService.auctionItemLikesFindByUserId(userId);
    }

}
