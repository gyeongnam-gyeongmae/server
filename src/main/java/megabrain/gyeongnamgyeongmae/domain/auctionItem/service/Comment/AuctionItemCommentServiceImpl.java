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
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.CommentNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemServiceImpl;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemCommentServiceImpl implements AuctionItemCommentService {

  private final AuctionItemCommentRepository auctionItemCommentRepository;
  private final UserRepository userRepository;
  private final AuctionItemRepository auctionItemRepository;
  private final UserServiceInterface userService;
  private final AuctionItemServiceImpl auctionItemService;

  @Transactional
  public void createAuctionItemComment(
      AuctionItemCommentRequest auctionItemCommentRequest, Long id) {

    User user = userService.findUserById(auctionItemCommentRequest.getUserId());
    AuctionItem auctionItem = auctionItemService.findAuctionItemById(id);

    Comment parentComment =
        auctionItemCommentRequest.getParentCommentId() != null
                && auctionItemCommentRequest.getParentCommentId() >= 1L
            ? findCommentById(auctionItemCommentRequest.getParentCommentId())
            : null;

    Comment comment = auctionItemCommentRequest.toEntity();
    comment.setUser(user);
    comment.setAuctionItem(auctionItem);
    comment.setParent(parentComment);

    auctionItem.plusCommentCount();
    saveComment(comment);
    auctionItemService.saveAuctionItem(auctionItem);
  }

  public void saveComment(Comment comment) {
    auctionItemCommentRepository.save(comment);
  }

  private Comment findCommentById(Long id) {
    return auctionItemCommentRepository
        .findById(id)
        .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
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
