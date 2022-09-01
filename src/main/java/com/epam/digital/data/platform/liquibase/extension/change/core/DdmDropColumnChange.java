/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.liquibase.extension.change.core;

import com.epam.digital.data.platform.liquibase.extension.DdmUtils;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.DropColumnChange;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;


/**
 * Drops an existing column from a table.
 */

@DatabaseChange(
    name = "dropColumn",
    description = "Drop existing column(s).\n" +
        "\n" +
        "To drop a single column, use the simple form of this element where the tableName and " +
        "columnName are specified as attributes. To drop several columns, specify the tableName " +
        "as an attribute, and then specify a set of nested <column> tags. If nested <column> tags " +
        "are present, the columnName attribute will be ignored.",
    priority = ChangeMetaData.PRIORITY_DEFAULT + 50,
    appliesTo = "column")
public class DdmDropColumnChange extends DropColumnChange {

    private Boolean historyFlag;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.addAll(DdmUtils.validateHistoryFlag(getHistoryFlag()));
        
        return Boolean.TRUE.equals(historyFlag) ?
            validationErrors.addError("'dropColumn' is not allowed") :
            validationErrors.addAll(super.validate(database));
    }

    public Boolean getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(Boolean historyFlag) {
        this.historyFlag = historyFlag;
    }
}
