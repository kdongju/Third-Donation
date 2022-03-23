import { useEffect, useState } from 'react';
import Breakpoint, { BreakpointProvider, setDefaultBreakpoints } from 'react-socks';
import { useSelector, useDispatch } from 'react-redux';
import { Link } from '@reach/router';
import { createGlobalStyle } from 'styled-components';
import useOnclickOutside from 'react-cool-onclickoutside';
import { fetchAccount } from '../../store/actions/thunks/account';
import { connectWallet } from '../../core/ethereum';

import * as selectors from '../../store/selectors';
import NotificationPopup from './NotificationPopup';
import ProfilePopup from './ProfilePopup';

// 기본 중단점을 설정
// xs, s, m, l, xl이 있음
// 이것을 명명해서 이름을 줄 수 있음
// ex) [{cat : 0}, {dog : 1000}]
setDefaultBreakpoints([{ xs: 0 }, { l: 1199 }, { xl: 1200 }]);

const GlobalStyles = createGlobalStyle`
  .account {
    a {
      display: inline-block;
      margin: 0 5px;
    }
  }
`;

const NavLink = (props) => (
  <Link
    {...props}
    getProps={({ isCurrent }) => {
      // the object returned here is passed to the
      // anchor element's props
      return {
        className: isCurrent ? 'active' : 'non-active',
      };
    }}
  />
);

/**
 * 탑 bar로 있을 헤더 컴포넌트
 * @param {*} param0
 * @returns
 */
const Header = ({ className }) => {
  const dispatch = useDispatch();
  const { data: account } = useSelector(selectors.accountState);

  const [statMenu, setStatMenu] = useState(false);
  const [supportMenu, setSupportMenu] = useState(false);
  const [adminMenu, setAdminMenu] = useState(false);
  const [testMenu, setTestMenu] = useState(false);
  const [artistMenu, setArtistMenu] = useState(false);
  /* 모바일 뷰 메뉴 */
  const [menu, setMenu] = useState(false);

  const onStatMenuClick = () => setStatMenu(!statMenu);
  const onSupportMenuClick = () => setSupportMenu(!supportMenu);
  const onAdminMenuClick = () => setAdminMenu(!adminMenu);
  const onTestMenuClick = () => setTestMenu(!testMenu);
  const onArtistMenuClick = () => setArtistMenu(!artistMenu);

  const closeStatMenu = () => setStatMenu(false);
  const closeSupportMenu = () => setSupportMenu(false);
  const closeAdminMenu = () => setAdminMenu(false);
  const closeTestMenu = () => setTestMenu(false);
  const closeArtistMenu = () => setArtistMenu(false);

  const refStatMenu = useOnclickOutside(() => closeStatMenu());
  const refSupportMenu = useOnclickOutside(() => closeSupportMenu());
  const refAdminMenu = useOnclickOutside(() => closeAdminMenu());
  const refTestMenu = useOnclickOutside(() => closeTestMenu());
  const refArtistMenu = useOnclickOutside(() => closeArtistMenu());

  const onConnectWallet = () => {
    connectWallet()
      .then((data) => {
        if (data.walletInstalled) {
          dispatch(fetchAccount(data.walletAddress));
        } else {
          alert('메타마스크 지갑을 설치하세요!');
        }
      })
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    const header = document.getElementById('myHeader');
    const totop = document.getElementById('scroll-to-top');
    // 내부 경계를 기준으로 현재 요소의 외부 경계의 거리
    const sticky = header.offsetTop;

    const scrollCallBack = window.addEventListener('scroll', () => {
      setMenu(false);
      if (window.pageYOffset > sticky) {
        header.classList.add('sticky');
        totop.classList.add('show');
      } else {
        header.classList.remove('sticky');
        totop.classList.remove('show');
      }
    });
    return () => {
      window.removeEventListener('scroll', scrollCallBack);
    };
  }, []);

  return (
    <header className={`navbar white ${className}`} id="myHeader">
      <GlobalStyles />
      <div className="container">
        <div className="row w-100-nav">
          <div className="logo px-0">
            <div className="navbar-title navbar-item">
              <NavLink to="/">
                <img src="/img/logo.png" className="img-fluid d-block" alt="#" />
                <img src="/img/logo-light.png" className="img-fluid d-none" alt="#" />
              </NavLink>
            </div>
          </div>

          <div className="search">
            <input
              id="quick_search"
              className="xs-hide"
              name="quick_search"
              placeholder="검색어를 입력하세요"
              type="text"
            />
          </div>

          <BreakpointProvider>
            <Breakpoint l down>
              {menu && (
                <div className="menu">
                  <div className="navbar-item">
                    <NavLink to="/explore" onClick={() => setMenu(!menu)}>
                      Marketplace
                    </NavLink>
                  </div>
                  <div className="navbar-item">
                    <div ref={refStatMenu}>
                      <div
                        className="dropdown-custom dropdown-toggle btn"
                        onClick={onStatMenuClick}>
                        Stat
                      </div>
                      {statMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeStatMenu}>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              Rankings
                            </NavLink>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              Activity
                            </NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="navbar-item">
                    <div ref={refSupportMenu}>
                      <div
                        className="dropdown-custom dropdown-toggle btn"
                        onClick={onSupportMenuClick}>
                        Support
                      </div>
                      {supportMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeSupportMenu}>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              공지사항
                            </NavLink>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              FAQ
                            </NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                  {/* TODO : 관리자 체크 */}
                  <div className="navbar-item">
                    <div ref={refAdminMenu}>
                      <div
                        className="dropdown-custom dropdown-toggle btn"
                        onClick={onAdminMenuClick}>
                        Admin
                      </div>
                      {adminMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeAdminMenu}>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              관리자메뉴1
                            </NavLink>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              관리자기능2
                            </NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                  {/* TODO : 장애인 예술가 체크 */}
                  <div className="navbar-item">
                    <div ref={refArtistMenu}>
                      <div
                        className="dropdown-custom dropdown-toggle btn"
                        onClick={onArtistMenuClick}>
                        Artist
                      </div>
                      {artistMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeArtistMenu}>
                            <NavLink to="/mint" onClick={() => setMenu(!menu)}>
                              작품 등록
                            </NavLink>
                            <NavLink to="#" onClick={() => setMenu(!menu)}>
                              장애인 예술가 메뉴1
                            </NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="navbar-item">
                    <div ref={refTestMenu}>
                      <div
                        className="dropdown-custom dropdown-toggle btn"
                        onClick={onTestMenuClick}>
                        테스트
                      </div>
                      {testMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeTestMenu}>
                            <NavLink to="/profile/1" onClick={() => setMenu(!menu)}>
                              프로필
                            </NavLink>
                            <NavLink to="/editProfile/1" onClick={() => setMenu(!menu)}>
                              프로필변경
                            </NavLink>
                            <NavLink to="/sell" onClick={() => setMenu(!menu)}>
                              작품 판매
                            </NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              )}
            </Breakpoint>

            <Breakpoint xl>
              <div className="menu">
                <div className="navbar-item">
                  <NavLink to="/explore">
                    Marketplace
                    <span className="lines"></span>
                  </NavLink>
                </div>
                <div className="navbar-item">
                  <div ref={refStatMenu}>
                    <div
                      className="dropdown-custom dropdown-toggle btn"
                      onMouseEnter={onStatMenuClick}
                      onMouseLeave={closeStatMenu}>
                      Stats
                      <span className="lines"></span>
                      {statMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeStatMenu}>
                            <NavLink to="#">Rankings</NavLink>
                            <NavLink to="#">Activity</NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                <div className="navbar-item">
                  <div ref={refSupportMenu}>
                    <div
                      className="dropdown-custom dropdown-toggle btn"
                      onMouseEnter={onSupportMenuClick}
                      onMouseLeave={closeSupportMenu}>
                      Support
                      <span className="lines"></span>
                      {supportMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeSupportMenu}>
                            <NavLink to="#">공지 사항</NavLink>
                            <NavLink to="#">FAQ</NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                {/* TODO : 관리자 체크 */}
                <div className="navbar-item">
                  <div ref={refAdminMenu}>
                    <div
                      className="dropdown-custom dropdown-toggle btn"
                      onMouseEnter={onAdminMenuClick}
                      onMouseLeave={closeAdminMenu}>
                      Admin
                      <span className="lines"></span>
                      {adminMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeAdminMenu}>
                            <NavLink to="#">회원 관리</NavLink>
                            <NavLink to="#">장애인</NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                {/* TODO : 장애인 예술가 체크 */}
                <div className="navbar-item">
                  <div ref={refArtistMenu}>
                    <div
                      className="dropdown-custom dropdown-toggle btn"
                      onMouseEnter={onArtistMenuClick}
                      onMouseLeave={closeArtistMenu}>
                      Artist
                      <span className="lines"></span>
                      {artistMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeArtistMenu}>
                            <NavLink to="/mint">작품 등록</NavLink>
                            <NavLink to="#">장애인 예술가 메뉴</NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                <div className="navbar-item">
                  <div ref={refTestMenu}>
                    <div
                      className="dropdown-custom dropdown-toggle btn"
                      onMouseEnter={onTestMenuClick}
                      onMouseLeave={closeTestMenu}>
                      테스트
                      <span className="lines"></span>
                      {testMenu && (
                        <div className="item-dropdown">
                          <div className="dropdown" onClick={closeTestMenu}>
                            <NavLink to="/ItemDetail/1">아이템 상세</NavLink>
                            <NavLink to="/nftDetail/1">소유 NFT 상세</NavLink>
                            <NavLink to="/profile/1">프로필</NavLink>
                            <NavLink to="/editProfile/1">프로필변경</NavLink>
                            <NavLink to="/donation">후원하기</NavLink>
                            <NavLink to="/sell">작품 판매</NavLink>
                            <NavLink to="/artistRegistration">예술가 등록</NavLink>
                            <NavLink to="/admin/grantArtist">예술가 승인</NavLink>
                            <NavLink to="/charityRegistration">자선단체 등록</NavLink>
                            <NavLink to="/admin/grantCharity">자선단체 승인</NavLink>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </Breakpoint>
          </BreakpointProvider>

          <div className="mainside">
            {account ? (
              <div className="account d-flex align-items-center">
                {/* TODO : 토큰 생성 버튼 관리자 권한 체크 */}
                <NavLink to="/createOptions">Create</NavLink>
                <NotificationPopup />
                <ProfilePopup />
              </div>
            ) : (
              <div>
                <a onClick={() => onConnectWallet()}>지갑 연결하기</a>
              </div>
            )}
          </div>
        </div>

        <button className="nav-icon" onClick={() => setMenu(!menu)}>
          <div className="menu-line white"></div>
          <div className="menu-line1 white"></div>
          <div className="menu-line2 white"></div>
        </button>
      </div>
    </header>
  );
};
export default Header;
