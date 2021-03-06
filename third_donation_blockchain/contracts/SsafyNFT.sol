// SPDX-License-Identifier: MIT
pragma solidity ^0.8.4;

import "./token/ERC721/extensions/ERC721Enumerable.sol";
import "./token/ERC721/extensions/ERC721URIStorage.sol";
import "./access/Ownable.sol";

/**
 * PJT Ⅰ - 과제 2) NFT Creator 구현
 * 상태 변수나 함수의 시그니처는 구현에 따라 변경할 수 있습니다.
 */
contract SsafyNFT is ERC721Enumerable, ERC721URIStorage, Ownable {
    using Strings for uint256;
    // 예술가 여부를 매핑함. O(1)
    mapping(address => bool) private _artistAddress;

    // 자선 단체 여부를 매핑함. O(1)
    mapping(address => bool) private _charityAddress;

    // 같은 파일 해시 여부를 매핑함. O(1)
    mapping(string => bool) private _isUsedFile;

    // 같은 tokenURI 존재 여부를 매핑함. O(1)
    mapping(string => bool) private _isUsedTokenURI;

    // 토큰을 민팅한 예술가의 지갑 주소를 매핑함. O(1)
    mapping(uint => address) private _tokenArtistAddress;

    constructor() ERC721("SsafyNFT", "SFT") {
        _artistAddress[owner()] = true;
    }

    /**
     * @dev Throws if called by any account other than the artist.
     * @dev 아티스트가 아닌 사람이 호출하면 Throw 함.
     */
    modifier onlyArtist() {
        require(_artistAddress[msg.sender], "Arist: caller is not the artist");
        _;
    }

    /**
     * @dev file과 tokenURI를 입력받아 동일 토큰의 존재 여부를 반환함.
     */
    function _existToken(string memory file, string memory _tokenURI) internal view virtual returns (bool) {
        return _isUsedFile[file] || _isUsedTokenURI[_tokenURI];
    }

    // 예술가 지갑 주소 등록
    function addArtistAddress(address artistAddress) public onlyOwner {
        _artistAddress[artistAddress] = true;
    }

    // 예술가 지갑 주소 삭제
    function deleteArtistAddress(address artistAddress) public onlyOwner {
        _artistAddress[artistAddress] = false;
    }

    // 자선 단체 지갑 주소 등록
    function addCharityAddress(address charityAddress) public onlyOwner {
        _charityAddress[charityAddress] = true;
    }

    // 자선 단체 지갑 주소 삭제
    function deleteCharityAddress(address charityAddress) public onlyOwner {
        _charityAddress[charityAddress] = false;
    }


    function current() public view returns (uint256) {
        return totalSupply();
    }

    function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal virtual override(ERC721, ERC721Enumerable) {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function _burn(uint256 tokenId) internal override(ERC721, ERC721URIStorage) {
        super._burn(tokenId);
    }

    function tokenURI(uint256 tokenId) public view override(ERC721, ERC721URIStorage) returns (string memory) {
        return super.tokenURI(tokenId);
    }

    function create(
        address to,
        string memory file,
        string memory _tokenURI
    ) public onlyArtist returns (uint256) {
        require(!_existToken(file, _tokenURI), "SsafyNFT: Already exist token");
        uint256 newTokenId = totalSupply() + 1;

        //NFT 민팅
        super._mint(to, newTokenId);

        //token 값들을 존재하는 토큰으로 변경
        _isUsedFile[file] = true;
        _isUsedTokenURI[_tokenURI] = true;

        //토큰 아아디에 해당하는 URI 변경
        super._setTokenURI(newTokenId, _tokenURI);

        //토큰을 민팅한 예술가 주소 담기
        _tokenArtistAddress[newTokenId] = to;

        //반환
        return newTokenId;
    }

    function supportsInterface(bytes4 interfaceId) public view override(ERC721, ERC721Enumerable) returns (bool) {
        return super.supportsInterface(interfaceId);
    }

    function _baseURI() internal pure override returns (string memory) {
        return "https://localhost:5999/token/";
    }

    function getTokenArtistAddress(uint256 _artTokenId) view public returns (address){
        return _tokenArtistAddress[_artTokenId];
    }

    function getCharity(address charity) view public returns (bool){
        return _charityAddress[charity];
    }


}
