import Web3 from 'web3';
import ABI from '../common/ABI';

const {
  CONTRACT_ABI: { TOKEN_ABI },
} = ABI;

const {
  CONTRACT_ABI: { ART_NFT_ABI },
} = ABI;

const {
  CONTRACT_ABI: { SALE_ABI },
} = ABI;

const rpcHttpUrl = 'http://20.196.209.2:8545';

const ssafyTokenContractAddress = '0x6C927304104cdaa5a8b3691E0ADE8a3ded41a333';

const ssafyTokenProvider = new Web3.providers.HttpProvider(rpcHttpUrl);

export const web3 = new Web3(ssafyTokenProvider);

export const ssafyTokenContract = new web3.eth.Contract(TOKEN_ABI, ssafyTokenContractAddress);

const artTokenContractAddress = '0xA59B280FF8438384677B29eBB37B739e4226A89D';
export const artTokenContract = new web3.eth.Contract(ART_NFT_ABI, artTokenContractAddress);

export const saleArtTokenContractAddress = '0x22bdf739A7Ab358bCB6Ba88E1ED84B1F075f6a37';
export const saleArtTokenContract = new web3.eth.Contract(SALE_ABI, saleArtTokenContractAddress);
