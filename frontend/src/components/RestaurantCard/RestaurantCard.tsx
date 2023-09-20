import { styled } from 'styled-components';
import { MouseEventHandler } from 'react';
import { useNavigate } from 'react-router-dom';
import ImageCarousel from '../@common/ImageCarousel';
import Love from '~/assets/icons/love.svg';
import ProfileImageList from '../@common/ProfileImageList';
import { FONT_SIZE, truncateText } from '~/styles/common';

import type { Celeb } from '~/@types/celeb.types';
import type { Restaurant } from '~/@types/restaurant.types';
import Modal from '~/components/@common/Modal';
import LoginModalContent from '~/components/LoginModalContent';
import useToggleLikeNotUpdate from '~/hooks/server/useToggleLikeNotUpdate';

interface RestaurantCardProps {
  restaurant: Restaurant;
  celebs: Celeb[];
  size?: string;
  type?: 'list' | 'map';
  setHoveredId?: React.Dispatch<React.SetStateAction<number>>;
}

function RestaurantCard({ restaurant, celebs, size, type = 'list', setHoveredId = () => {} }: RestaurantCardProps) {
  const { id, images, name, roadAddress, category, phoneNumber } = restaurant;
  const { isModalOpen, closeModal, openModal, toggleRestaurantLike, isLiked } = useToggleLikeNotUpdate(restaurant);

  const navigate = useNavigate();

  const onMouseEnter = () => setHoveredId(restaurant.id);
  const onMouseLeave = () => setHoveredId(null);
  const onClick = () => {
    navigate(`/restaurants/${id}?celebId=${celebs[0].id}`);
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
        data-cy="음식점 카드"
        aria-label={`${name} 카드`}
        tabIndex={0}
      >
        <StyledImageViewer>
          <ImageCarousel images={images} type={type} />
          <LikeButton aria-label="좋아요" type="button" onClick={toggle}>
            <Love width={20} fill={isLiked ? 'red' : '#000'} fillOpacity={0.8} aria-hidden="true" />
          </LikeButton>
        </StyledImageViewer>
        <StyledInfoSection type={type}>
          <StyledInfo>
            <StyledCategory>{category}</StyledCategory>
            <StyledName role="columnheader">{name}</StyledName>
            <StyledAddress>{roadAddress}</StyledAddress>
            <StyledAddress>{phoneNumber}</StyledAddress>
          </StyledInfo>
          <StyledProfileImageSection>
            {celebs && <ProfileImageList celebs={celebs} size={size} />}
          </StyledProfileImageSection>
        </StyledInfoSection>
      </StyledContainer>
      <Modal title="로그인 및 회원 가입" isOpen={isModalOpen} close={closeModal}>
        <LoginModalContent />
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
  line-height: 20px;
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

const StyledInfoSection = styled.section<{ type: 'list' | 'map' }>`
  padding: ${({ type }) => (type === 'list' ? 'none' : '0.8rem')};
`;
