import { element, by, ElementFinder } from 'protractor';

export class InternalCodeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-internal-code div table .btn-danger'));
    title = element.all(by.css('jhi-internal-code div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class InternalCodeUpdatePage {
    pageTitle = element(by.id('jhi-internal-code-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    descriptionInput = element(by.id('field_description'));
    externalSelect = element(by.id('field_external'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async externalSelectLastOption() {
        await this.externalSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async externalSelectOption(option) {
        await this.externalSelect.sendKeys(option);
    }

    getExternalSelect(): ElementFinder {
        return this.externalSelect;
    }

    async getExternalSelectedOption() {
        return this.externalSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class InternalCodeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-internalCode-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-internalCode'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
