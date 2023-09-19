import { useState } from 'react';
import styled from 'styled-components';
import { shallow } from 'zustand/shallow';
import CelebIcon from '~/assets/icons/celeb.svg';
import TextButton from '../@common/Button';
import { Modal, ModalContent } from '../@common/Modal';
import { FONT_SIZE } from '~/styles/common';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import { Celeb } from '~/@types/celeb.types';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import ProfileImage from '../@common/ProfileImage';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import NavItem from '../@common/NavItem';
import { isEqual } from '~/utils/compare';
import { RestaurantCategory } from '~/@types/restaurant.types';

interface FilterOptionsModalProps {
  isModalOpen: boolean;
  openModal: VoidFunction;
  closeModal: VoidFunction;
  celebOptions: Celeb[];
}

function FilterOptionsModal({ isModalOpen, openModal, closeModal, celebOptions }: FilterOptionsModalProps) {
  const [filterName, setFilterName] = useState('celeb');

  const [category, setCelebId, setCurrentPage, setRestaurantCategory] = useRestaurantsQueryStringState(
    state => [state.restaurantCategory, state.setCelebId, state.setCurrentPage, state.setRestaurantCategory],
    shallow,
  );

  const clickCeleb = (e: React.MouseEvent<HTMLElement>) => {
    const currentCelebId = Number(e.currentTarget.dataset.id);

    setCelebId(currentCelebId);
    setCurrentPage(0);
  };

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label as RestaurantCategory;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
  };

  return (
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
                      {id === -1 ? <CelebIcon /> : <ProfileImage name={name} imageUrl={profileImageUrl} size="32px" />}
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
  );
}

export default FilterOptionsModal;

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

const StyledSelectContainer = styled.div`
  width: 100%;
  height: 40vh;

  background: transparent;

  overflow-y: auto;
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

const StyledNavItemButton = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;

const StyledCelebName = styled.div`
  font-family: SUIT-Medium, sans-serif;
`;

const StyledChannelName = styled.div`
  padding-top: 0.4rem;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;
