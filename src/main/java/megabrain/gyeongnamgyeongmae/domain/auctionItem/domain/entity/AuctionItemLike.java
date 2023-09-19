package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.global.commons.BaseTimeEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "AuctionItemLike")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionItemLike extends BaseTimeEntity {

    @EmbeddedId
    private AuctionItemLikePK id;

    @MapsId("auctionItemId")
    @ManyToOne
    @JoinColumn(name = "auctionItemId")
    private AuctionItem auctionItem;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Builder
    public AuctionItemLike(AuctionItemLikePK id, AuctionItem auctionItem, Member member) {
        this.id = id;
        this.auctionItem = auctionItem;
        this.member = member;
    }

}
