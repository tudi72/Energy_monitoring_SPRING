const minLengthValidator = (value, minLength) => {
    return value.length >= minLength;
};

const requiredValidator = value => {
    return value.trim() !== '';
};

const doubleValidator = value => {
    return value > 0.0
}

const descriptionValidator = value => {
    const re = /^[\w\s]*$/;
    return re.test(String(value).toLowerCase());
}
const validate = (value, rules) => {
    let isValid = true;

    for (let rule in rules) {

        switch (rule) {
            case 'minLength':
                isValid = isValid && minLengthValidator(value, rules[rule]);
                break;

            case 'isRequired':
                isValid = isValid && requiredValidator(value);
                break;

            case 'doubleValidator':
                isValid = isValid && doubleValidator(value);
                break;

            case 'descriptionValidator':
                isValid = isValid && descriptionValidator(value);
                break;

            default:
                isValid = true;
        }

    }

    return isValid;
};

export default validate;
