/* eslint-disable @typescript-eslint/ban-ts-comment */
import { useRef, useState } from 'react';
import { styled, css } from 'styled-components';
import { Wrapper } from '@googlemaps/react-wrapper';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { shallow } from 'zustand/shallow';
import useBooleanState from '~/hooks/useBooleanState';
import useScrollDirection from '~/hooks/useScrollDirection';
import useScrollEnd from '~/hooks/useScrollEnd';
import Logo from '~/assets/icons/logo-icon.svg';
import MapIcon from '~/assets/icons/map.svg';
import ListIcon from '~/assets/icons/list.svg';
import UserIcon from '~/assets/icons/etc/user.svg';
import LoveIcon from '~/assets/icons/love.svg';
import CelebIcon from '~/assets/icons/celeb.svg';
import { FONT_SIZE } from '~/styles/common';

import SearchBar from '~/components/SearchBar';
import NavItem from '~/components/@common/NavItem';
import RestaurantCardList from '~/components/RestaurantCardList';
import Footer from '~/components/@common/Footer';
import Map from '~/components/@common/Map';
import { Modal, ModalContent } from '~/components/@common/Modal';
import ProfileImage from '~/components/@common/ProfileImage';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import { RestaurantCategory } from '~/@types/restaurant.types';
import { isEqual } from '~/utils/compare';
import useScrollBlock from '~/hooks/useScrollBlock';
import TextButton from '~/components/@common/Button';
import useCeleb from '~/hooks/server/useCeleb';

function MobileMainPage() {
  const refs = [useRef(), useRef(), useRef()];
  const navigator = useNavigate();
  const scrollDirection = useScrollDirection();
  const { isEnd } = useScrollEnd({ direction: 'Y', threshold: 200 });
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { value: isListShowed, toggle: toggleShowedList } = useBooleanState(false);
  const { data: celebOptions, isSuccess } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: () => getCelebs(),
    suspense: true,
  });
  const [filterName, setFilterName] = useState('celeb');
  const { getCelebs } = useCeleb();

  const [category, celebId, setCelebId, setCurrentPage, setRestaurantCategory] = useRestaurantsQueryStringState(
    state => [
      state.restaurantCategory,
      state.celebId,
      state.setCelebId,
      state.setCurrentPage,
      state.setRestaurantCategory,
    ],
    shallow,
  );

  const selectedCeleb = celebOptions.find(({ id }) => id === celebId);

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label as RestaurantCategory;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
  };

  const clickCeleb = (e: React.MouseEvent<HTMLElement>) => {
    const currentCelebId = Number(e.currentTarget.dataset.id);

    setCelebId(currentCelebId);
    setCurrentPage(0);
  };

  useScrollBlock(refs);

  return (
    <>
      <StyledMobileMainPageContainer>
        <StyledTopNavBar ref={refs[0]}>
          <header>
            <Logo width={32} />
            <h5>celuveat</h5>
            <div />
          </header>
          <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
            <SearchBar />
          </Wrapper>
        </StyledTopNavBar>

        {isListShowed ? (
          <StyledToggleButton
            type="button"
            onClick={toggleShowedList}
            isHide={isEnd}
            isNavBarHide={scrollDirection.y === 'down'}
            ref={refs[1]}
          >
            <span>지도</span>
            <MapIcon width={24} />
          </StyledToggleButton>
        ) : (
          <StyledToggleButton
            type="button"
            onClick={toggleShowedList}
            isHide={false}
            isNavBarHide={false}
            ref={refs[1]}
          >
            <span>리스트</span>
            <ListIcon width={20} stroke="#fff" />
          </StyledToggleButton>
        )}

        <StyledBottomNavBar isHide={scrollDirection.y === 'down'} ref={refs[2]}>
          <StyledNavBarButton type="button" onClick={() => navigator('/restaurants/like')}>
            <NavItem label="위시리스트" icon={<LoveIcon width={24} />} />
          </StyledNavBarButton>
          <StyledFilterButton type="button" onClick={openModal}>
            {selectedCeleb ? (
              <NavItem
                label={selectedCeleb.youtubeChannelName.replace('@', '')}
                icon={<ProfileImage name={selectedCeleb.name} imageUrl={selectedCeleb.profileImageUrl} size="32px" />}
              />
            ) : (
              <NavItem label="필터" icon={<CelebIcon width={24} />} />
            )}
          </StyledFilterButton>
          {!isSuccess && (
            <StyledNavBarButton
              type="button"
              onClick={() => {
                navigator('/signUp');
              }}
            >
              <NavItem label="로그인" icon={<UserIcon width={24} />} />
            </StyledNavBarButton>
          )}
        </StyledBottomNavBar>

        <StyledMobileLayout isListShowed={isListShowed}>
          <div>
            <RestaurantCardList />
            <Footer />
          </div>
          <Map />
        </StyledMobileLayout>
      </StyledMobileMainPageContainer>

      <Modal isOpen={isModalOpen} open={openModal} close={closeModal}>
        <ModalContent>
          <StyledFilterContainer>
            <StyledFilterButtonContainer>
              <TextButton type="button" text="셀럽 선택" onClick={() => setFilterName('celeb')} colorType="light" />
              <TextButton
                type="button"
                text="카테고리 선택"
                onClick={() => setFilterName('category')}
                colorType="light"
              />
            </StyledFilterButtonContainer>
            {filterName === 'celeb' && (
              <StyledFilterItem>
                <StyledSelectContainer>
                  {[OPTION_FOR_CELEB_ALL, ...celebOptions].map(({ id, name, youtubeChannelName, profileImageUrl }) => (
                    <StyledDropDownOption data-id={id} onClick={clickCeleb}>
                      <div>
                        {id === -1 ? (
                          <CelebIcon />
                        ) : (
                          <ProfileImage name={name} imageUrl={profileImageUrl} size="32px" />
                        )}
                        <div>
                          <StyledCelebName>{name}</StyledCelebName>
                          <StyledChannelName>{youtubeChannelName}</StyledChannelName>
                        </div>
                      </div>
                    </StyledDropDownOption>
                  ))}
                </StyledSelectContainer>
              </StyledFilterItem>
            )}
            {filterName === 'category' && (
              <StyledFilterItem>
                <li>
                  {RESTAURANT_CATEGORY.map(({ icon, label }) => (
                    <StyledNavItemButton
                      aria-label={label}
                      data-label={label}
                      type="button"
                      onClick={clickRestaurantCategory}
                    >
                      <NavItem label={label} icon={icon} isShow={isEqual(category, label)} />
                    </StyledNavItemButton>
                  ))}
                </li>
              </StyledFilterItem>
            )}
          </StyledFilterContainer>
        </ModalContent>
      </Modal>
    </>
  );
}

export default MobileMainPage;

const StyledFilterButton = styled.button`
  border: none;
  background: none;
`;

const StyledFilterContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3.2rem 0;

  height: 50vh;
`;

const StyledFilterButtonContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;

  gap: 0 0.4rem;
`;

const StyledFilterItem = styled.div`
  display: flex;
  flex-direction: column;

  & > li > * {
    margin: 0.4rem auto;
  }
`;

const StyledNavItemButton = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;

const StyledMobileMainPageContainer = styled.div`
  width: 100%;
  height: 100vh;
`;

const StyledTopNavBar = styled.nav`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  position: fixed;
  z-index: 10;

  width: 100%;
  height: 88px;

  padding: 0.2rem 0.8rem;

  background-color: var(--white);
  box-shadow: var(--map-shadow);

  & > header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    width: 100%;
    height: 44px;

    & > div {
      width: 32px;

      font-size: ${FONT_SIZE.lg};
    }
  }
`;

const StyledMobileLayout = styled.main<{ isListShowed: boolean }>`
  position: absolute;
  top: 88px;

  width: 100vw;
  height: calc(100vh - 88px - 64px);

  & > div:first-child {
    position: absolute;
    top: 0;
    z-index: 1;

    width: 100%;
    height: 100vh;

    background-color: var(--white);

    ${({ isListShowed }) => !isListShowed && 'display: none;'}

    & > *:first-child {
      min-height: calc(100vh - 88px);
    }
  }
`;

const StyledToggleButton = styled.button<{ isHide: boolean; isNavBarHide: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  bottom: 88px;

  gap: 0.8rem;

  width: 100px;

  z-index: 20;
  left: calc(50% - 50px);

  height: 40px;

  border: none;
  border-radius: 50px;
  background-color: var(--black);

  transition: 0.2s ease-in-out;

  & > * {
    color: var(--white);
  }

  &:hover {
    scale: 1.04;
    box-shadow: var(--map-shadow);
  }

  ${({ isNavBarHide }) =>
    isNavBarHide &&
    css`
      transition: 0.4s ease-in-out;
      transform: translateY(64px);
    `}

  ${({ isHide }) =>
    isHide &&
    css`
      opacity: 0;
      transition: 0.4s ease-in-out;
      transform: translateY(64px);
    `}
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: 40vh;

  background: transparent;

  overflow-y: auto;
`;

const StyledBottomNavBar = styled.nav<{ isHide: boolean }>`
  display: flex;
  justify-content: space-around;
  align-items: flex-start;

  position: fixed;
  bottom: 0;
  z-index: 20;

  width: 100vw;
  height: 64px;

  background-color: var(--white);

  border-top: 1px solid var(--gray-1);

  ${({ isHide }) => isHide && 'transform: translateY(64px)'};

  transition: 0.4s ease-in-out;
`;

const StyledDropDownOption = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;

  height: 60px;

  padding: 0 1.8rem;

  font-size: ${FONT_SIZE.md};

  cursor: pointer;

  & + & {
    border-bottom: 1px solid var(--gray-1);
  }

  &:first-child {
    border-bottom: 1px solid var(--gray-1);
  }

  & > div {
    display: flex;
    align-items: center;
    gap: 0 1.2rem;
  }

  &:hover {
    background-color: var(--gray-1);
  }
`;

const StyledCelebName = styled.div`
  font-family: SUIT-Medium, sans-serif;
`;

const StyledChannelName = styled.div`
  padding-top: 0.4rem;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledNavBarButton = styled.button`
  border: none;
  outline: none;

  background: none;
`;
