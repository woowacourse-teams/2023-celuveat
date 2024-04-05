import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { Celeb } from '~/@types/celeb.types';
import { FONT_SIZE } from '~/styles/common';
import ProfileImage from '../@common/ProfileImage';
import useOnClickBlock from '~/hooks/useOnClickBlock';

function CelebProfile({ name, profileImageUrl, id }: Celeb) {
  const navigate = useNavigate();
  const register = useOnClickBlock({
    callback: () => navigate(`/celeb/${id}`),
  });

  return (
    <StyledCeleb {...register}>
      <ProfileImage name={name} imageUrl={profileImageUrl} size="64px" boxShadow />
      <span>{name}</span>
    </StyledCeleb>
  );
}

export default CelebProfile;

const StyledCeleb = styled.div`
  display: flex !important;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  font-size: ${FONT_SIZE.sm};

  cursor: pointer;
`;
