import { styled } from 'styled-components';
import { useNavigate } from 'react-router-dom';
import LoginButton from '~/components/@common/LoginButton';
import CeluveatIcon from '~/assets/icons/celuveat-login-icon.svg';
import { FONT_SIZE } from '~/styles/common';

function SignUpPage() {
  const navigate = useNavigate();

  const clickHomeButton = () => {
    navigate('/');
  };

  return (
    <StyledContainer>
      <StyledIconWrapper>
        <CeluveatIcon />
        <StyledName>Celuveat</StyledName>
      </StyledIconWrapper>
      <StyledLoginButtonBox>
        <StyledHomeButton onClick={clickHomeButton}>비회원으로 이용하기</StyledHomeButton>
        <StyledLine>-------------------- sign in --------------------</StyledLine>
        <LoginButton type="kakao" />
        <LoginButton type="google" />
      </StyledLoginButtonBox>
    </StyledContainer>
  );
}

export default SignUpPage;

const StyledContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100vh;

  background-color: var(--primary-6);
`;

const StyledIconWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  margin-bottom: 10rem;
`;

const StyledName = styled.h2`
  color: var(--white);
`;

const StyledLoginButtonBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: end;
  align-items: center;

  position: absolute;
  bottom: 1%;

  width: 100%;

  padding: 2rem;
`;

const StyledHomeButton = styled.button`
  width: 100%;
  height: 48px;

  border: none;
  border-radius: 8px;
  background-color: var(--white);

  font-size: ${FONT_SIZE.md};
  font-weight: 600;
  box-shadow: var(--shadow);
`;

const StyledLine = styled.span`
  margin: 1.6rem 0;

  color: var(--white);
  font-size: ${FONT_SIZE.md};
`;
