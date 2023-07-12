import styled from 'styled-components';
import Tag from '~/components/@common/Tag/Tag';

interface TagsStyleProps {
  width: number;
}

interface TagsProps extends TagsStyleProps {
  texts: string[];
}

function Tags({ width, texts }: TagsProps) {
  return (
    <StyledTagsContainer width={width}>
      {texts.map(text => (
        <Tag text={text} />
      ))}
    </StyledTagsContainer>
  );
}

export default Tags;

const StyledTagsContainer = styled.div<TagsStyleProps>`
  display: flex;

  width: ${({ width }) => `${width}px`};

  flex-wrap: nowrap;

  gap: 1rem;
`;
