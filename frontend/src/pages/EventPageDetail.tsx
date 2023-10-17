import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { SERVER_IMG_URL } from '~/constants/url';

function EventPageDetail() {
  return (
    <StyledContainer>
      <StyledLink to="/">
        <StyledTitle>← 홈으로 돌아가기</StyledTitle>
      </StyledLink>
      <img alt="이벤트 상세" src={`${SERVER_IMG_URL}banner/event-detail.jpeg`} />
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
