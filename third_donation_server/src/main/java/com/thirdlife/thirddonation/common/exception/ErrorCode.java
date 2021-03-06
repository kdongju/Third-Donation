package com.thirdlife.thirddonation.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러코드들을 나열한 enum 클래스입니다.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다"),
    CANNOT_WISH_MYSELF(BAD_REQUEST, "자기 자신의 토큰은 찜할 수 없습니다"),
    CANNOT_SELL_OTHERS(BAD_REQUEST, "소유자와 판매자가 일치하지 않습니다"),
    CANNOT_BUY_MINE(BAD_REQUEST, "판매자와 구매자가 같을 수 없습니다."),
    CANNOT_BUY_DISABLED(BAD_REQUEST, "판매 중지된 작품을 구입할 수 없습니다."),
    CANNOT_BUY_SOLD_OUT(BAD_REQUEST, "이미 판매 완료된 작품입니다."),
    CANNOT_DOWN_AUTHORITY(BAD_REQUEST, "이미 관리자입니다."),
    CANNOT_EMPTY_IMAGE(BAD_REQUEST, "이미지를 보내야 합니다."),
    CANNOT_WRONG_MIME(BAD_REQUEST, "잘못된 타입의 이미지입니다"),
    CANNOT_WRONG_FILTER(BAD_REQUEST, "잘못된 필터링 요청입니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    USER_INVALID(UNAUTHORIZED, "사용자가 일치하지 않습니다."),
    AUTHORITY_INVALID(UNAUTHORIZED, "사용자의 권한 수준이 낮습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저의 정보를 찾을 수 없습니다."),
    CHARITY_NOT_FOUND(NOT_FOUND, "해당 지갑 주소에 해당하는 자선 단체 정보를 찾을 수 없습니다."),
    OWNER_NOT_FOUND(NOT_FOUND, "해당 Owner 주소에 해당하는 유저 정보를 찾을 수 없습니다."),
    SELLER_NOT_FOUND(NOT_FOUND, "해당 Seller 정보를 찾을 수 없습니다."),
    BUYER_NOT_FOUND(NOT_FOUND, "해당 Buyer 정보를 찾을 수 없습니다."),
    NFT_NOT_FOUND(NOT_FOUND, "해당 token id를 찾을 수 없습니다."),
    SALE_NOT_FOUND(NOT_FOUND, "판매 정보를 찾을 수 없습니다."),
    ARTIST_REQUEST_NOT_FOUND(NOT_FOUND, "해당 예술가 신청을 찾을 수 없습니다."),
    ARTIST_NOT_FOUND(NOT_FOUND, "해당 예술가를 찾을 수 없습니다."),
    FOLLOW_NOT_FOUND(NOT_FOUND, "follow 정보를 찾을 수 없습니다."),
    WISH_NOT_FOUND(NOT_FOUND, "찜하기 정보를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),
    ARTICLE_NOT_FOUND(NOT_FOUND, "해당 글을 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    USER_ID_DUPLICATE(CONFLICT, "중복된 사용자 Wallet address 입니다."),
    ARTIST_DUPLICATE(CONFLICT, "이미 장애인 예술가 등록 (신청)된 사용자입니다."),
    FOLLOW_DUPLICATE(CONFLICT, "이미 following 중인 artist 입니다."),
    WISH_DUPLICATE(CONFLICT, "이미 찜하기 등록된 토큰입니다."),

    /* 500 ERROR : 서버에서 예기치 않은 에러 발생 */
    DATABASE_SERVER_ERROR(INTERNAL_SERVER_ERROR, "예기치 않은 오류가 발생했습니다."),
    SPRING_SERVER_ERROR(INTERNAL_SERVER_ERROR, "예기치 않은 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
