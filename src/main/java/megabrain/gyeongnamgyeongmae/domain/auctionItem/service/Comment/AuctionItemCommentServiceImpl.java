package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemCommentRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemCommentServiceImpl implements AuctionItemCommentService {

  private final AuctionItemCommentRepository auctionItemCommentRepository;
  private final UserRepository userRepository;
  private final AuctionItemRepository auctionItemRepository;

  @Transactional
  public void createAuctionItemComment(
      AuctionItemCommentRequest auctionItemCommentRequest, Long id, Long userId) {
    User user =
        (User)
            this.userRepository
                .findById(userId)
                .orElseThrow(
                    () -> {
                      return new RuntimeException("유저를 찾을수 없습니다");
                    });
    AuctionItem auctionItem =
        (AuctionItem)
            this.auctionItemRepository
                .findById(id)
                .orElseThrow(
                    () -> {
                      return new RuntimeException("경매품을 찾을수 없습니다");
                    });
    Comment parentComment = null;
    Long parentCommentId = auctionItemCommentRequest.getParentCommentId();
    if (parentCommentId >= 1L) {
      parentComment =
          (Comment)
              this.auctionItemCommentRepository
                  .findById(parentCommentId)
                  .orElseThrow(
                      () -> {
                        return new RuntimeException("부모 댓글을 찾을수 없습니다");
                      });
    }

    Comment comment =
        Comment.builder()
            .content(auctionItemCommentRequest.getContent())
            .user(user)
            .auctionItem(auctionItem)
            .parent(parentComment)
            .build();
    this.auctionItemCommentRepository.save(comment);
    auctionItem.setComment_count(auctionItem.getComment_count() + 1L);
    this.auctionItemRepository.save(auctionItem);
  }

  @Transactional(readOnly = true)
  public List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id) {
    List<Comment> commentEntityList =
        this.auctionItemCommentRepository.findByAuctionItemCommentByAuctionId(id);
    return commentEntityList.stream()
        .map(AuctionItemCommentParentDto::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateAuctionItemComment(
      AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest) {
    Comment comment =
        (Comment)
            this.auctionItemCommentRepository
                .findById(auctionItemCommentUpdateRequest.getCommentId())
                .orElseThrow(
                    () -> {
                      return new RuntimeException("댓글을 찾을수 없습니다");
                    });
    if (!Objects.equals(comment.getUser().getId(), auctionItemCommentUpdateRequest.getUserId())) {
      throw new RuntimeException("댓글 작성자가 아닙니다");
    } else {
      comment.setContent(auctionItemCommentUpdateRequest.getContent());
      this.auctionItemCommentRepository.save(comment);
    }
  }

  @Transactional
  public void deleteAuctionItemComment(
      AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest) {
    Comment comment =
        (Comment)
            this.auctionItemCommentRepository
                .findById(auctionItemCommentDeleteRequest.getCommentId())
                .orElseThrow(
                    () -> {
                      return new RuntimeException("댓글을 찾을수 없습니다");
                    });
    if (!Objects.equals(comment.getUser().getId(), auctionItemCommentDeleteRequest.getUserId())) {
      throw new RuntimeException("댓글 작성자가 아닙니다");
    } else {
      comment.setRemoved(true);
      this.auctionItemCommentRepository.save(comment);
    }
  }
}
