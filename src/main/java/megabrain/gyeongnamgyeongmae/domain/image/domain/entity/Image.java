package megabrain.gyeongnamgyeongmae.domain.image.domain.entity;

import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@NoArgsConstructor
@Entity
@Table(name = "Image")
@Getter
public class Image extends BaseTimeEntity {

    @Id
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_from")
    private String imageFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private AuctionItem auctionItem;

    @Column(name = "removed")
    private boolean removed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="comment_id")
    private Comment comment;

    @Builder
    public Image(String name, String url, String imageFrom) {
        this.imageName = name;
        this.imageUrl = url;
        this.imageFrom = imageFrom;
    }

    public void setAuctionItem(AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
    }
}
