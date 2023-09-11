package megabrain.gyeongnamgyeongmae.Image.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.commons.BaseTimeEntity;

@Entity
@Table(name = "images", indexes = @Index(name = "url", columnList = "image_url"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "image_url")
  private String url;

  @Column(name = "removed")
  private boolean removed;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "auction_id")
  private AuctionItem auctionItem;

  @Builder
  public Image(String url, AuctionItem auctionItem) {
    this.url = url;
    this.auctionItem = auctionItem;
  }
}
