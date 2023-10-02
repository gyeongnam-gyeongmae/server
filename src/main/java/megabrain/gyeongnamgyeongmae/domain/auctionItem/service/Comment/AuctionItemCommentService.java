package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemCommentRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.CommentNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemCommentService implements AuctionItemCommentServiceInterface {

  private final AuctionItemCommentRepository auctionItemCommentRepository;
  private final UserServiceInterface userService;
  private final AuctionItemServiceInterface auctionItemService;

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

  @Override
  public Comment findCommentById(Long id) {
    return auctionItemCommentRepository
        .findById(id)
        .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
  }

  @Transactional(readOnly = true)
  public List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id) {
    auctionItemService.findAuctionItemById(id);
    List<Comment> commentEntityList =
        auctionItemCommentRepository.findByAuctionItemCommentByAuctionId(id);
    return commentEntityList.stream()
        .map(AuctionItemCommentParentDto::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateAuctionItemComment(
      AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest) {
    Comment comment = findCommentById(auctionItemCommentUpdateRequest.getCommentId());
    userService.findUserById(auctionItemCommentUpdateRequest.getUserId());
    comment.setContent(auctionItemCommentUpdateRequest.getContent());
    auctionItemCommentRepository.save(comment);
  }

  @Transactional
  public void deleteAuctionItemComment(
      AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest) {
    Comment comment = findCommentById(auctionItemCommentDeleteRequest.getCommentId());
    userService.findUserById(auctionItemCommentDeleteRequest.getUserId());
    comment.deleteComment();
    auctionItemCommentRepository.save(comment);
  }
}
