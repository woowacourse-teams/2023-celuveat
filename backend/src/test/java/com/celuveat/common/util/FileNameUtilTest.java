package com.celuveat.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("파일 이름 유틸리티(FileNameUtil) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class FileNameUtilTest {

    @ParameterizedTest
    @CsvSource(value = {
            "index.html --> index",
            "image.webp --> image",
            "imagePENG.png --> imagePENG",
    }, delimiterString = " --> ")
    void 파일_이름에서_확장자_제거_테스트(String fileName, String fileNameWithoutExtension) {
        String extracted = FileNameUtil.removeExtension(fileName);

        assertThat(extracted).isEqualTo(fileNameWithoutExtension);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "image --> image.webp",
            "imagePENG --> imagePENG.webp",
    }, delimiterString = " --> ")
    void 파일_이름에_webp_확장자_추가_테스트(String fileNameWithoutExtension, String fileNameWithWebpExtension) {
        String extracted = FileNameUtil.attachWebpExtension(fileNameWithoutExtension);

        assertThat(extracted).isEqualTo(fileNameWithWebpExtension);
    }
}
