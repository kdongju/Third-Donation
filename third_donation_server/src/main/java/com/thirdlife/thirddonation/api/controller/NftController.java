package com.thirdlife.thirddonation.api.controller;

import com.thirdlife.thirddonation.api.dto.request.nft.NftMintRequest;
import com.thirdlife.thirddonation.api.dto.request.nft.NftSalesRegisterRequest;
import com.thirdlife.thirddonation.api.exception.CustomException;
import com.thirdlife.thirddonation.api.exception.ErrorCode;
import com.thirdlife.thirddonation.api.service.nft.NftService;
import com.thirdlife.thirddonation.api.service.user.UserService;
import com.thirdlife.thirddonation.common.model.response.BaseResponseBody;
import com.thirdlife.thirddonation.db.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NFT 작품 관련 요청을 처리하는 컨트롤러입니다.
 */
@Slf4j
@Validated
@Api(tags = "NFT 관리")
@RestController
@RequestMapping("${request.path.api}${request.path.nfts}")
@RequiredArgsConstructor
public class NftController {

    private final UserService userService;
    private final NftService nftService;

    /**
     * Post 요청시 전송받은 정보로 NFT를 등록합니다.
     * 만약 있다면 ResponseEntity 객체를 반환합니다.
     *
     * @param nftMintRequest NftMintRequest
     * @return ResponseEntity
     */
    @PostMapping("/items")
    @ApiOperation(value = "NFT 정보 등록 (민팅)",
            notes = "<strong>NFT</strong>를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> mint(
            @Valid @RequestBody @ApiParam(value = "NFT 정보", required = true)
                    NftMintRequest nftMintRequest) {

        nftService.createNft(nftMintRequest);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    /**
     * Post 요청시 전송받은 정보로 NFT 판매 정보를 등록합니다.
     * 만약 있다면 ResponseEntity 객체를 반환합니다.
     *
     * @param nftSalesRegisterRequest NftSalesRegisterRequest
     * @return ResponseEntity
     */
    @PostMapping("/sales")
    @ApiOperation(value = "NFT 판매 등록",
            notes = "<strong>NFT 판매 정보</strong>를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createSales(
            @Valid @RequestBody @ApiParam(value = "NFT 판매 정보", required = true)
                    NftSalesRegisterRequest nftSalesRegisterRequest) {

        nftService.createSales(nftSalesRegisterRequest);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    /**
     * NFT 판매 중지 정보를 등록합니다.
     *
     * @param salesId Long
     * @return ResponseEntity
     */
    @DeleteMapping("/sales/{salesId}")
    @ApiOperation(value = "NFT 판매 중지",
            notes = "<strong>NFT 판매</strong>를 중지한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> disableSales(
            @PathVariable @ApiParam(value = "판매 아이디", required = true)
                    Long salesId) {

        nftService.disableSales(salesId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}