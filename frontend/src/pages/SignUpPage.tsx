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
        <StyledLine> or </StyledLine>
        <LoginButton type="kakao" />
        <LoginButton type="google" />
      </StyledLoginButtonBox>
    </StyledContainer>
  );
}

export default SignUpPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 4.8rem;

  height: 100%;

  background-color: var(--primary-6);
`;

const StyledIconWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
`;

const StyledName = styled.h2`
  color: var(--white);
`;

const StyledLoginButtonBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: end;
  align-items: center;

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

const StyledLine = styled.div`
  margin: 1.2rem 0;

  color: var(--white);
  font-size: ${FONT_SIZE.md};
`;
