import { ChangeEvent } from 'react';
import styled from 'styled-components';
import useBaseURLState from '~/hooks/store/useBaseURLState';

function ServerSelectBox() {
  const { setMSW, setDev, setProd } = useBaseURLState();

  const handleServerState = (e: ChangeEvent<HTMLSelectElement>) => {
    if (e.target.value === 'dev') setDev();
    if (e.target.value === 'msw') setMSW();
    if (e.target.value === 'prod') setProd();
  };

  return (
    <StyledSelectBox id="environmentSelect" onChange={handleServerState}>
      <option value="dev">dev</option>
      <option value="msw">msw</option>
      <option value="prod">prod</option>
    </StyledSelectBox>
  );
}

export default ServerSelectBox;

const StyledSelectBox = styled.select`
  position: absolute;
  right: 200px;

  width: 100px;
  height: 40px;
`;
