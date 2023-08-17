import { styled } from 'styled-components';
import { MouseEventHandler } from 'react';
import ImageCarousel from '../@common/ImageCarousel';
import Love from '~/assets/icons/love.svg';
import ProfileImageList from '../@common/ProfileImageList';
import { FONT_SIZE, truncateText } from '~/styles/common';

import type { Celeb } from '~/@types/celeb.types';
import type { Restaurant } from '~/@types/restaurant.types';
import useToggleRestaurantLike from '~/hooks/server/useToggleRestaurantLike';
import PopUpContainer from '~/components/PopUpContainer';
import { Modal, ModalContent } from '~/components/@common/Modal';
import LoginModalContent from '~/components/LoginModalContent';

interface RestaurantCardProps {
  restaurant: Restaurant;
  celebs: Celeb[];
  size?: string;
  type?: 'list' | 'map';
  setHoveredId?: React.Dispatch<React.SetStateAction<number>>;
}

function RestaurantCard({ restaurant, celebs, size, type = 'list', setHoveredId = () => {} }: RestaurantCardProps) {
  const { id, images, name, roadAddress, category, phoneNumber } = restaurant;
  const { isModalOpen, closeModal, toggleRestaurantLike } = useToggleRestaurantLike(restaurant);

  const onMouseEnter = () => setHoveredId(restaurant.id);
  const onMouseLeave = () => setHoveredId(null);
  const onClick = () => {
    window.open(`/restaurants/${id}?celebId=${celebs[0].id}`, '_blank');
  };

  const toggle: MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();

    toggleRestaurantLike();
  };

  return (
    <>
      <StyledContainer
        onClick={onClick}
        onMouseEnter={onMouseEnter}
        onMouseLeave={onMouseLeave}
        data-cy={`${name} 카드`}
        aria-label={`${name} 카드`}
      >
        <StyledImageViewer>
          <ImageCarousel images={images} type={type} />
          <LikeButton aria-label="좋아요" type="button" onClick={toggle}>
            <Love width={20} fill={restaurant.isLiked ? 'red' : '#000'} fillOpacity={0.8} aria-hidden="true" />
          </LikeButton>
        </StyledImageViewer>
        <section>
          <StyledInfo>
            <StyledCategory>{category}</StyledCategory>
            <StyledName role="columnheader">{name}</StyledName>
            <StyledAddress>{roadAddress}</StyledAddress>
            <StyledAddress>{phoneNumber}</StyledAddress>
          </StyledInfo>
          <StyledProfileImageSection>
            {celebs && <ProfileImageList celebs={celebs} size={size} />}
          </StyledProfileImageSection>
          <PopUpContainer isShowImg />
        </section>
      </StyledContainer>
      <Modal>
        <ModalContent isShow={isModalOpen} title="로그인 및 회원 가입" closeModal={closeModal}>
          <LoginModalContent />
        </ModalContent>
      </Modal>
    </>
  );
}

export default RestaurantCard;

const StyledContainer = styled.li`
  display: flex;
  flex-direction: column;
  justify-content: start;
  gap: 0.8rem;

  width: 100%;
  height: 100%;

  & > section {
    display: flex;
    justify-content: space-between;
  }

  cursor: pointer;
`;

const StyledImageViewer = styled.div`
  position: relative;
`;

const StyledInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  position: relative;

  width: 100%;

  padding: 0.4rem;
`;

const StyledName = styled.h5`
  ${truncateText(1)}
`;

const StyledAddress = styled.span`
  ${truncateText(1)}
  color: var(--gray-4);
  font-size: ${FONT_SIZE.md};
`;

const StyledCategory = styled.span`
  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;

const StyledProfileImageSection = styled.div`
  align-self: flex-end;
`;

const LikeButton = styled.button`
  position: absolute;
  top: 12px;
  right: 12px;
  z-index: 8;

  border: none;
  background-color: transparent;

  &:hover {
    transform: scale(1.2);
  }
`;
