import { element, by, ElementFinder } from 'protractor';

export class ExternalCodeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-external-code div table .btn-danger'));
    title = element.all(by.css('jhi-external-code div h2#page-heading span')).first();

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

export class ExternalCodeUpdatePage {
    pageTitle = element(by.id('jhi-external-code-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    descriptionInput = element(by.id('field_description'));

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

export class ExternalCodeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-externalCode-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-externalCode'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
