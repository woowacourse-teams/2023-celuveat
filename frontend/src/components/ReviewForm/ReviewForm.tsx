import { useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';

import { FONT_SIZE } from '~/styles/common';

import StarRating from '~/components/@common/StarRating/StarRating';
import ReviewImageForm from '~/components/ReviewImageForm';
import TextButton from '~/components/@common/Button';

import type { StarRate } from '~/components/@common/StarRating/StarRating';
import type { RestaurantReviewData } from '~/@types/api.types';
import type { ReviewSubmitButtonType } from '~/@types/review.types';

const SUBMIT_BUTTON_TEXT = {
  create: '등록하기',
  update: '수정하기',
  report: '신고하기',
} as const;

interface ReviewFormProps {
  type: ReviewSubmitButtonType;
  reviewId?: number;
}

function ReviewForm({ type, reviewId }: ReviewFormProps) {
  const { id: restaurantId } = useParams();
  const qc = useQueryClient();
  const reviewData: RestaurantReviewData = qc.getQueryData(['restaurantReview', restaurantId]);
  const [text, setText] = useState('');
  const [images, setImages] = useState<string[]>([]);
  const [rate, setRate] = useState<StarRate>(0);

  const { createReview, updateReview, postReviewReport } = useRestaurantReview();

  const resetFormData = () => {
    setText('');
    setImages([]);
    setRate(0);
  };

  const deleteReviewImage = (reviewImageId: number) => {
    setImages(images.filter((_, id) => id !== reviewImageId));
  };

  const onUploadReviewImage: React.ChangeEventHandler<HTMLInputElement> = e => {
    const file = e.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        setImages([...images, reader.result as string]);
      };

      reader.readAsDataURL(file);
    } else {
      setImages(null);
    }
  };

  const onClickStarRate: React.MouseEventHandler<HTMLButtonElement> = e => {
    const clickedStarRate = Number(e.currentTarget.dataset.rate) as StarRate;

    setRate(clickedStarRate);
  };

  const onChange: React.ChangeEventHandler<HTMLTextAreaElement> = e => {
    setText(e.target.value);
  };

  const submitReviewForm: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.preventDefault();

    const formData = new FormData();

    formData.append('images', JSON.stringify(images));
    formData.append('content', text);
    formData.append('rate', String(rate));

    switch (type) {
      case 'create':
        formData.append('restaurantId', restaurantId);
        createReview(formData);
        break;
      case 'update':
        updateReview({ reviewId, body: formData });
        break;
      case 'report':
        postReviewReport({ reviewId, content: text });
        break;
      default:
        throw new Error('해당 타입의 review Form은 지원하지 않습니다.');
    }

    resetFormData();

    window.location.reload();
  };

  useEffect(() => {
    if (type === 'update') {
      const targetReview = reviewData?.reviews.find(review => review.id === reviewId);
      setText(targetReview.content);
      setImages(targetReview.reviewImageUrls);
    }
  }, [reviewData]);

  return (
    <StyledReviewFormContainer>
      <span>별점 등록 ({rate}/5)</span>
      <StarRating rate={rate} onRateClick={onClickStarRate} />
      <span>사진 등록하기</span>
      <ReviewImageForm images={images} upload={onUploadReviewImage} deleteImage={deleteReviewImage} />
      <StyledTextArea placeholder="여기에 리뷰를 적어주세요." value={text} onChange={onChange} />
      <TextButton
        type="submit"
        onClick={submitReviewForm}
        text={SUBMIT_BUTTON_TEXT[type]}
        colorType="dark"
        disabled={text.length === 0}
      />
    </StyledReviewFormContainer>
  );
}

export default ReviewForm;

const StyledReviewFormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 3.6rem 0;

  width: 100%;
`;

const StyledTextArea = styled.textarea`
  height: 30vh;

  padding: 0.8rem;

  border: none;
  border-radius: 10px;
  background-color: var(--gray-2);

  font-size: ${FONT_SIZE.md};
  text-align: start;
  resize: vertical;
`;
