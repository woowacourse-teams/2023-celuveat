import { useParams } from 'react-router-dom';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import ProfileImage from '~/components/@common/ProfileImage';

function RestaurantDetail() {
  const { id } = useParams();

  return (
    <>
      <Header />
      <main>
        <section>
          <h3>여기에 음식점 제목{id}</h3>
          <div>
            <div>조회수 : 10000회, 좋아요 : 130회</div>
            <div>공유하기 저장</div>
          </div>
        </section>
        <section>
          <img src="https://random.imagecdn.app/500/500" alt="asd" />
          <img src="https://random.imagecdn.app/500/500" alt="asd" />
          <img src="https://random.imagecdn.app/500/500" alt="asd" />
          <img src="https://random.imagecdn.app/500/500" alt="asd" />
          <img src="https://random.imagecdn.app/500/500" alt="asd" />
        </section>
        <section>
          <div>
            <h5>엄청난 셀럽 이 다녀간 맛집</h5>
            <div>셀럽 아이디, 이외의 셀럽 정보</div>
            <ProfileImage imageUrl="https://avatars.githubusercontent.com/u/51052049?v=4" name="셀럽" size="56px" />
          </div>
          <div>
            <div>주소 : ~</div>
            <div>카테고리 : ~</div>
            <div>전화번호 : ~</div>
            <div>영업 시간 : ~~</div>
            <div>메뉴 : ~~</div>
          </div>
          <div>
            <div>메인 영상</div>
            <img src="https://random.imagecdn.app/500/500" alt="asd" />
          </div>
          <div>
            <div>이 셀럽이 간 다른 식당</div>
            <img src="https://random.imagecdn.app/500/500" alt="asd" />
          </div>
          <div>
            <div>주변 다른 식당</div>
            <img src="https://random.imagecdn.app/500/500" alt="asd" />
          </div>
          <div>지도 공간</div>
        </section>
        <section>
          <div> 네이버 바로가기 </div>
          <div> 공유하기 </div>
          <div>정보 수정 제안</div>
        </section>
      </main>
      <Footer />
    </>
  );
}

export default RestaurantDetail;
