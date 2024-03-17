import { styled, css } from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { DropDown } from 'celuveat-ui-library';

import { getCelebs } from '~/api/celeb';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';

import CelebNavItem from '~/components/CelebDropDown/CelebNavItem';
import CelebDropDownOption from '~/components/CelebDropDown/CelebDropDownOption';

import useMediaQuery from '~/hooks/useMediaQuery';
import { useCelebSelect } from '~/components/CelebDropDown/hooks/useCelebSelect';

function CelebDropDown() {
  const { isMobile } = useMediaQuery();

  const { selectedCeleb, selectCeleb } = useCelebSelect();

  const { data: celebOptions } = useQuery({
    queryKey: ['celebOptions'],
    queryFn: getCelebs,
    suspense: true,
  });

  return (
    <DropDown>
      <DropDown.Trigger isCustom>
        <StyledNavItemWrapper>
          <CelebNavItem celeb={selectedCeleb} />
        </StyledNavItemWrapper>
      </DropDown.Trigger>
      <DropDown.Options as="ul" isCustom>
        <StyledDropDownWrapper isMobile={isMobile}>
          <StyledSelectContainer>
            {[OPTION_FOR_CELEB_ALL, ...celebOptions].map(celeb => (
              <DropDown.Option as="li" key={celeb.id} onClick={selectCeleb(celeb)} isCustom>
                <StyledDropDownOption>
                  <CelebDropDownOption celeb={celeb} />
                </StyledDropDownOption>
              </DropDown.Option>
            ))}
          </StyledSelectContainer>
        </StyledDropDownWrapper>
      </DropDown.Options>
    </DropDown>
  );
}

export default CelebDropDown;

const StyledNavItemWrapper = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;

const StyledDropDownWrapper = styled.ul<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  align-content: center;

  position: absolute;
  top: calc(100% + 16px);
  left: 0;
  z-index: 1;

  ${({ isMobile }) =>
    isMobile
      ? css`
          width: 90vw;
          height: 60vh;
          max-height: 440px;
        `
      : css`
          width: 380px;
          height: 440px;
        `}

  padding: 1.2rem 0;

  border-radius: ${BORDER_RADIUS.md};
  background: var(--white);

  box-shadow: var(--shadow);
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: 100%;

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
