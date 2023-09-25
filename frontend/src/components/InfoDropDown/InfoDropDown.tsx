import { useQuery } from '@tanstack/react-query';
import { useRef } from 'react';
import { styled } from 'styled-components';
import { ProfileData } from '~/@types/api.types';
import { getProfile } from '~/api/user';

import InfoButton from '~/components/@common/InfoButton';
import InfoDropDownOption from '~/components/InfoDropDown/InfoDropDownOption';
import { OPTION_FOR_NOT_USER, OPTION_FOR_USER } from '~/constants/options';
import useBooleanState from '~/hooks/useBooleanState';
import useOnClickOutside from '~/hooks/useOnClickOutside';

interface DropDownProps {
  isOpen?: boolean;
  externalOnClick?: (e?: React.MouseEvent<HTMLElement>) => void;
  label: string;
}

function InfoDropDown({ externalOnClick, isOpen = false, label }: DropDownProps) {
  const { value: isShow, toggle: onToggleDropDown, setFalse: closeDropDown } = useBooleanState(isOpen);
  const ref = useRef();
  useOnClickOutside(ref, closeDropDown);

  const { data, isSuccess } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
  });

  const options = isSuccess ? OPTION_FOR_USER : OPTION_FOR_NOT_USER;

  const onSelection = () => (event?: React.MouseEvent<HTMLLIElement>) => {
    if (externalOnClick) externalOnClick(event);
  };

  return (
    <StyledInfoDropDown aria-hidden>
      <StyledInfoButtonWrapper ref={ref} onClick={onToggleDropDown} aria-label={label}>
        <InfoButton profile={data} isShow={isShow} isSuccess={isSuccess} />
      </StyledInfoButtonWrapper>

      {isShow && (
        <StyledDropDownWrapper aria-hidden>
          <StyledSelectContainer>
            {options.map(({ id, value }) => (
              <InfoDropDownOption key={id} value={value} onClick={onSelection()} />
            ))}
          </StyledSelectContainer>
        </StyledDropDownWrapper>
      )}
    </StyledInfoDropDown>
  );
}

export default InfoDropDown;

const StyledInfoButtonWrapper = styled.button`
  border: none;
  background: transparent;

  cursor: pointer;
  outline: none;
`;

const StyledInfoDropDown = styled.div`
  display: relative;
`;

const StyledDropDownWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: center;

  position: absolute;
  top: calc(100% - 8px);
  right: 18px;
  z-index: 1000;

  width: 216px;

  border-radius: 10px;
  background: white;

  font-size: 1.4rem;

  box-shadow: var(--shadow);
`;

const StyledSelectContainer = styled.div`
  width: 100%;
  height: fit-content;

  margin: 1rem 0;

  background: transparent;

  overflow-y: auto;
`;
