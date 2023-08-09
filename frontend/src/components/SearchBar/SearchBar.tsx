import { styled } from 'styled-components';
import { useEffect, useRef, useState } from 'react';
import SearchIcon from '../../assets/icons/search.svg';
import useMapState from '~/hooks/store/useMapState';
import { FONT_SIZE } from '~/styles/common';

function SearchBar() {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const [, setWidget] = useState<google.maps.places.Autocomplete | null>(null);
  const setCenter = useMapState(state => state.setCenter);

  useEffect(() => {
    if (!inputRef.current) return;

    const autocomplete = new google.maps.places.Autocomplete(inputRef.current);
    autocomplete.setFields(['name', 'geometry', 'types']);
    autocomplete.setTypes(['(regions)']);
    autocomplete.setComponentRestrictions({ country: ['kr'] });

    setWidget(autocomplete);

    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();

      if (!place.geometry) return;

      setCenter({ lat: place.geometry.location.lat(), lng: place.geometry.location.lng() });
    });
  }, []);

  return (
    <StyledContainer>
      <StyledInput placeholder="지역으로 검색하기" ref={inputRef} />
      <StyledButton type="button">
        <SearchIcon />
      </StyledButton>
    </StyledContainer>
  );
}

export default SearchBar;

const StyledContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: relative;

  width: 382px;
  height: 48px;

  border: 1px solid var(--gray-2);
  border-radius: 40px;
  background: var(--white);
  box-shadow: var(--shadow);
`;

const StyledInput = styled.input`
  flex: 1;

  height: 100%;

  padding: 0 1.2rem;

  border: none;
  background-color: transparent;

  font-size: ${FONT_SIZE.md};
  outline: none;
`;

const StyledButton = styled.button`
  position: absolute;
  right: 0.6rem;

  width: 36px;
  height: 36px;

  padding: 1.2rem;

  border: none;
  border-radius: 100%;
  background-color: var(--primary-6);

  color: var(--white);
`;
