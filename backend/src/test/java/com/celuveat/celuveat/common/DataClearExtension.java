package com.celuveat.celuveat.common;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DataClearExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        DataCleaner dataCleaner = getDataCleaner(context);
        dataCleaner.clear();
    }

    private DataCleaner getDataCleaner(ExtensionContext extensionContext) {
        return SpringExtension.getApplicationContext(extensionContext)
                .getBean(DataCleaner.class);
    }
}
