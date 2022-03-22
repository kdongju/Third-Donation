export const connectWallet = () =>
  new Promise((resolve, reject) => {
    if (window.ethereum) {
      window.ethereum
        .request({ method: 'eth_requestAccounts' })
        .then((accounts) =>
          resolve({
            walletInstalled: true,
            walletAddress: accounts,
          }),
        )
        .catch((error) => reject(error));
    } else {
      resolve({
        walletInstalled: false,
      });
    }
  });
