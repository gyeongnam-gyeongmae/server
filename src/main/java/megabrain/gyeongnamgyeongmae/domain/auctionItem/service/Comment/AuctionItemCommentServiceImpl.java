package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.CommentLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.CommentLikePK;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemCommentRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.CommentLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentLikeDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.CommentNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemCommentServiceImpl implements AuctionItemCommentService {

  private final AuctionItemCommentRepository auctionItemCommentRepository;
  private final UserServiceInterface userService;
  private final AuctionItemService auctionItemService;
  private final CommentLikeRepository commentLikeRepository;

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

    if (parentComment == null) {
      auctionItem.plusCommentCount();
    }

    saveComment(comment);
    auctionItemService.saveAuctionItem(auctionItem);
  }

  private void saveComment(Comment comment) {
    auctionItemCommentRepository.save(comment);
  }

  @Override
  public Comment findCommentById(Long id) {
    return auctionItemCommentRepository
        .findById(id)
        .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id, Long finderId) {
    auctionItemService.findAuctionItemById(id);
    List<Comment> commentEntityList = auctionItemCommentRepository.findByAuctionItemCommentByAuctionId(id);

    List<AuctionItemCommentParentDto> commentDtoList = commentEntityList.stream()
            .map(AuctionItemCommentParentDto::of)
            .collect(Collectors.toList());

    for(int i = 0; i < commentDtoList.size(); i++){
      Boolean isLiked = commentLikeRepository.findById(new CommentLikePK(commentDtoList.get(i).getId(), finderId)).isPresent();
      commentDtoList.get(i).setIsLiked(isLiked);
    }
    return commentDtoList;
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

  @Override
  @Transactional
  public void likeAuctionItemComment(CommentLikeDto commentLikeDto) {
    Comment comment = findCommentById(commentLikeDto.getCommentId());
    User user = userService.findUserById(commentLikeDto.getUserId());

    CommentLikePK commentLikePK =
        new CommentLikePK(commentLikeDto.getCommentId(), commentLikeDto.getUserId());

    CommentLike commentLike = commentLike(commentLikePK);

    if (commentLike != null) {
      deleteCommentLike(commentLike);
      comment.minusLikeCount();
    } else {
      commentLike = createAndSaveCommentLike(comment, user, commentLikePK);
      comment.plusLikeCount();
      saveCommentLike(commentLike);
    }
    auctionItemCommentRepository.save(comment);
  }

  private CommentLike commentLike(CommentLikePK commentLikePK) {
    return commentLikeRepository.findById(commentLikePK).orElse(null);
  }

  private void deleteCommentLike(CommentLike commentLike) {
    commentLikeRepository.delete(commentLike);
  }

  private CommentLike createAndSaveCommentLike(
      Comment comment, User user, CommentLikePK commentLikePK) {
    CommentLike commentLike =
        CommentLike.builder().id(commentLikePK).comment(comment).user(user).build();
    saveCommentLike(commentLike);
    return commentLike;
  }

  private void saveCommentLike(CommentLike commentLike) {
    commentLikeRepository.save(commentLike);
  }
}
