import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import NavItem from '../@common/NavItem';
import ProfileImage from '../@common/ProfileImage';
import CelebIcon from '~/assets/icons/celeb.svg';
import { getCelebs } from '~/api/celeb';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import FilterOptionsModal from './FilterOptionsModal';
import useBooleanState from '~/hooks/useBooleanState';

function FilterButton() {
  const celebId = useRestaurantsQueryStringState(state => state.celebId);
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);

  const { data: celebOptions } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: () => getCelebs(),
    suspense: true,
  });

  const selectedCeleb = celebOptions.find(({ id }) => id === celebId);

  return (
    <>
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

      {isModalOpen && (
        <FilterOptionsModal
          isModalOpen={isModalOpen}
          openModal={openModal}
          closeModal={closeModal}
          celebOptions={celebOptions}
        />
      )}
    </>
  );
}

export default FilterButton;

const StyledFilterButton = styled.button`
  border: none;
  background: none;
`;
