import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import TextButton from '~/components/@common/Button';
import { SERVER_IMG_URL } from '~/constants/url';

function EventPageDetail() {
  const navigate = useNavigate();
  const clickButton = () => navigate('/event-form');

  return (
    <StyledContainer>
      <StyledLink to="/">
        <StyledTitle>← 홈으로 돌아가기</StyledTitle>
      </StyledLink>
      <img alt="이벤트 상세" src={`${SERVER_IMG_URL}banner/event-detail.jpeg`} />
      <TextButton type="button" text="등록하기" colorType="dark" onClick={clickButton} />
    </StyledContainer>
  );
}

export default EventPageDetail;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  box-sizing: content-box;

  max-width: 980px;

  margin: 0 auto;

  padding-bottom: 4.4rem;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;

const StyledTitle = styled.h5`
  margin: 1.6rem 0.8rem 0;
`;
