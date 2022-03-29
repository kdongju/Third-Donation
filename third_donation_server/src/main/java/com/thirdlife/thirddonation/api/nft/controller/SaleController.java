package com.thirdlife.thirddonation.api.nft.controller;

import com.thirdlife.thirddonation.api.nft.dto.SaleInfoDto;
import com.thirdlife.thirddonation.api.nft.dto.request.SalesRegisterRequest;
import com.thirdlife.thirddonation.api.nft.dto.response.SaleListResponse;
import com.thirdlife.thirddonation.api.nft.service.SaleService;
import com.thirdlife.thirddonation.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NFT 판매 관련 요청을 처리하는 컨트롤러입니다.
 */
@Slf4j
@Validated
@Api(tags = "NFT 판매 관리")
@RestController
@RequestMapping("${request.path.api}${request.path.nfts}/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    /**
     * Post 요청시 전송받은 정보로 NFT 판매 정보를 등록합니다.
     * 만약 있다면 ResponseEntity 객체를 반환합니다.
     *
     * @param salesRegisterRequest SalesRegisterRequest
     * @return ResponseEntity
     */
    @PostMapping
    @ApiOperation(value = "NFT 판매 등록",
            notes = "<strong>NFT 판매 정보</strong>를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createSales(
            @Valid @RequestBody @ApiParam(value = "NFT 판매 정보", required = true)
                    SalesRegisterRequest salesRegisterRequest) {

        saleService.createSales(salesRegisterRequest);

        return ResponseEntity.status(200)
                .body(BaseResponseBody.builder().statusCode(200).message("Success").build());
    }

    /**
     * 판매중인 NFT 리스트 조회합니다.
     *
     * @param pageable Pageable
     * @return ResponseEntity
     */
    @GetMapping
    @ApiOperation(
            value = "NFT 판매 리스트",
            notes = "판매자 조회시 sellerId 붙여줌니다. <br>/api/nfts/sales?page=0&size=5&sellerId=9")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<SaleListResponse> getSalesList(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            @ApiParam(value = "페이지네이션", required = true) final Pageable pageable,
            @ApiParam(value = "조회할 판매자 ID") final Long sellerId) {

        Page<SaleInfoDto> salesList =
                sellerId != null ? saleService.getSalesListBySellerId(sellerId, pageable) :
                        saleService.getSalesList(pageable);

        return ResponseEntity.status(200).body(
                SaleListResponse.builder()
                        .statusCode(200).message("Success").data(salesList.getContent()).build()
        );
    }

    /**
     * NFT 판매 중지 정보를 등록합니다.
     *
     * @param salesId Long
     * @return ResponseEntity
     */
    @PatchMapping("/{salesId}/cancel")
    @ApiOperation(value = "NFT 판매 중지",
            notes = "<strong>NFT 판매</strong>를 중지한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> disableSales(
            @PathVariable @ApiParam(value = "판매 아이디", required = true)
                    Long salesId) {

        saleService.disableSales(salesId);

        return ResponseEntity.status(200)
                .body(BaseResponseBody.builder().statusCode(200).message("Success").build());
    }
}
