import styled from 'styled-components';
import Tag from '~/components/@common/Tag/Tag';

interface TagsProps {
  texts: string[];
}

function Tags({ texts }: TagsProps) {
  return (
    <StyledTagsContainer>
      {texts.map(text => (
        <Tag text={text} />
      ))}
    </StyledTagsContainer>
  );
}

export default Tags;

const StyledTagsContainer = styled.div`
  display: flex;

  flex-wrap: nowrap;

  gap: 1rem;
`;
