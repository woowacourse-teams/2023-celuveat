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
      <option value="prod">prod</option>
      <option value="dev">dev</option>
      <option value="msw">msw</option>
    </StyledSelectBox>
  );
}

export default ServerSelectBox;

const StyledSelectBox = styled.select`
  position: fixed;
  top: 20px;
  left: 200px;
  z-index: 10000;

  width: 100px;
  height: 40px;
`;
