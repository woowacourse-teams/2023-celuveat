import styled, { css } from 'styled-components';
import TextButton from '~/components/@common/Button';
import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

function WithdrawalModalContent() {
  const { isMobile } = useMediaQuery();

  const onClick = () => {};

  return (
    <StyledWithdrawalModalContentWrapper>
      <h4>주의하세요</h4>

      <StyleWithdrawalContent>
        <StyledWithdrawalText>탈퇴시 삭제/유지되는 정보를 확인하세요!</StyledWithdrawalText>
        <StyledWithdrawalText>한 번 삭제되는 정보는 복구가 불가능합니다.</StyledWithdrawalText>
      </StyleWithdrawalContent>

      <StyleWithdrawalDetailWrapper>
        <StyledWithdrawalDetailText>내가 좋아요한 음식점 데이터</StyledWithdrawalDetailText>
        <StyledWithdrawalDetailText>위시리스트</StyledWithdrawalDetailText>
        <StyledWithdrawalDetailText>음식점 댓글</StyledWithdrawalDetailText>
      </StyleWithdrawalDetailWrapper>

      <StyledButtonWrapper isMobile={isMobile}>
        <TextButton type="button" text="취소하기" colorType="light" width="100%" onClick={onClick} />
        <TextButton type="button" text="탈퇴하기" colorType="dark" width="100%" onClick={onClick} />
      </StyledButtonWrapper>
    </StyledWithdrawalModalContentWrapper>
  );
}

export default WithdrawalModalContent;

const StyledButtonWrapper = styled.div<{ isMobile: boolean }>`
  display: flex;
  gap: 3rem;

  margin-top: 2rem;

  ${({ isMobile }) =>
    isMobile &&
    css`
      flex-direction: column;
    `}
`;

const StyledWithdrawalText = styled.span`
  color: gray;
  font-size: ${FONT_SIZE.md};
  line-height: 20px;
`;

const StyleWithdrawalContent = styled.div`
  display: flex;
  flex-direction: column;
`;

const StyleWithdrawalDetailWrapper = styled.ul`
  display: flex;
  flex-direction: column;
`;

const StyledWithdrawalDetailText = styled.li`
  color: var(--gray-4);
  font-size: ${FONT_SIZE.md};
  line-height: 24px;
`;

const StyledWithdrawalModalContentWrapper = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;
