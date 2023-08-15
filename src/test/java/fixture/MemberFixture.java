package fixture;

import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.member.dto.MemberCreateRequest;

public class MemberFixture {
  public static final Member MEMBER1 = new Member("member1@gyeongmae.com", "!Member123", "user1");

  public static final Member MEMBER2 = new Member("member2@gyeongmae.com", "!Member123", "user2");

  public static final MemberCreateRequest MEMBER_REGISTRATION_REQUEST =
      new MemberCreateRequest("newmember@gyeongmae.com", "!Newmember123", "newMember");

  public static final Member NEW_MEMBER =
      new Member("newmember@gyeongmae.com", "!Newmember123", "newMember");
}
