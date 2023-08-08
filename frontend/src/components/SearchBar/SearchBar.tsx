import { styled } from 'styled-components';
import { useEffect, useRef, useState } from 'react';
import SearchIcon from '../../assets/icons/search.svg';
import useMapState from '~/hooks/store/useMapState';

function SearchBar() {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const [widget, setWidget] = useState<google.maps.places.Autocomplete | null>(null);
  const setCenter = useMapState(state => state.setCenter);

  useEffect(() => {
    if (!inputRef.current) return;

    const autocomplete = new google.maps.places.Autocomplete(inputRef.current);
    autocomplete.setFields(['place_id', 'geometry', 'name']);

    setWidget(autocomplete);

    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();

      if (!place.geometry) return;

      setCenter({ lat: place.geometry.location.lat(), lng: place.geometry.location.lng() });

      inputRef.current.innerHTML = place.name;
    });
  }, []);

  return (
    <StyledContainer>
      <StyledInput placeholder="지역으로 검색하기" ref={inputRef} />
      <StyledButton type="submit">
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

  width: 382px;
  height: 48px;

  padding: 0 1.2rem;

  border: 1px solid var(--gray-2);
  border-radius: 40px;
  background: var(--white);
  box-shadow: 0 4px 12px 0 rgb(0 0 0 / 5%), 0 1px 2px 0 rgb(0 0 0 / 8%);
`;

const StyledInput = styled.input`
  flex: 1;

  height: 100%;

  border: none;
  background-color: transparent;

  font-size: medium;
  outline: none;
`;

const StyledButton = styled.button`
  width: 36px;
  height: 36px;

  padding: 1.2rem;

  border: none;
  border-radius: 100%;
  background-color: var(--red-2);

  color: var(--white);
`;
