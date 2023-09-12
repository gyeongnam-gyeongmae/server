package megabrain.gyeongnamgyeongmae.domain.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.global.commons.BaseTimeEntity;

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
