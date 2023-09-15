import { css, styled } from 'styled-components';
import { useEffect, useRef, useState } from 'react';
import SearchIcon from '../../assets/icons/search.svg';
import useMapState from '~/hooks/store/useMapState';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import useMediaQuery from '~/hooks/useMediaQuery';

function SearchBar() {
  const { isMobile } = useMediaQuery();
  const inputRef = useRef<HTMLInputElement | null>(null);
  const [, setWidget] = useState<google.maps.places.Autocomplete | null>(null);
  const setCenter = useMapState(state => state.setCenter);

  useEffect(() => {
    if (!inputRef.current) return;

    const autocomplete = new google.maps.places.Autocomplete(inputRef.current);
    autocomplete.setFields(['name', 'geometry', 'types']);
    autocomplete.setComponentRestrictions({ country: ['kr'] });

    setWidget(autocomplete);

    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();

      if (!place.geometry) return;

      setCenter({ lat: place.geometry.location.lat(), lng: place.geometry.location.lng() });
    });
  }, []);

  useEffect(() => {
    const input = inputRef.current;
    const originalAddEventListener = input.addEventListener;
    const addEventListenerWrapper = (type: string, listener: EventListenerOrEventListenerObject) => {
      if (type !== 'keydown') originalAddEventListener.call(input, type, listener);
      if (type === 'keydown') {
        const newListener = (event: KeyboardEvent) => {
          if (event.key === 'Enter' && !document.querySelector('.pac-item-selected')) {
            const simulatedDownArrow = new KeyboardEvent('keydown', {
              key: 'ArrowDown',
              code: 'ArrowDown',
              keyCode: 40,
              which: 40,
            });
            if (typeof listener === 'function') listener.call(input, simulatedDownArrow);
          }
          if (typeof listener === 'function') listener.call(input, event);
        };
        originalAddEventListener.call(input, type, newListener);
      }
    };

    input.addEventListener = addEventListenerWrapper;

    return () => {
      input.addEventListener = originalAddEventListener;
    };
  }, []);

  const clearInput = () => {
    inputRef.current.value = '';
  };

  return (
    <StyledContainer isMobile={isMobile}>
      <StyledInput
        placeholder="지역으로 검색하기"
        data-cy="지역 검색"
        ref={inputRef}
        onFocus={clearInput}
        isMobile={isMobile}
      />
      {!isMobile && (
        <StyledButton type="button" aria-hidden>
          <SearchIcon aria-label="검색" />
        </StyledButton>
      )}
    </StyledContainer>
  );
}

export default SearchBar;

const StyledContainer = styled.div<{ isMobile: boolean }>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: relative;

  ${({ isMobile }) =>
    isMobile
      ? css`
          width: 100%;
          height: 36px;

          margin: 0.4rem 0;

          border: 1px solid var(--gray-1);
          border-radius: ${BORDER_RADIUS.xs};
          background: var(--gray-1);
        `
      : css`
          width: 382px;
          height: 48px;

          border: 1px solid var(--gray-2);
          border-radius: 40px;
          background: var(--white);
          box-shadow: var(--shadow);
        `}
`;

const StyledInput = styled.input<{ isMobile: boolean }>`
  border: none;
  background-color: transparent;

  ${({ isMobile }) =>
    isMobile
      ? css`
          width: 100%;
          height: 32px;
        `
      : css`
          flex: 1;

          height: 100%;

          padding: 0 1.2rem;

          outline: none;

          font-size: ${FONT_SIZE.md};
        `}
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
