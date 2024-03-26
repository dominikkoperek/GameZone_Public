// Author: Habib Mhamadi
// Email: habibmhamadi@gmail.com

function MultiSelectTag(el, customs = {shadow: false, rounded: true}) {
    let element = null
    let options = null
    let customSelectContainer = null
    let wrapper = null
    let btnContainer = null
    let body = null
    let inputContainer = null
    let inputBody = null
    let input = null
    let button = null
    let drawer = null
    let ul = null
    init()

    let gameResetButton = document.getElementById("game-reset-button");
    // Remove all selected items when reset button clicked

    gameResetButton.addEventListener("click",function (){
        const selectedItems = inputContainer.querySelectorAll('.item-container');
        selectedItems.forEach(item => {
            const value = item.querySelector('.item-label').dataset.value;
            const unselectOption = options.find(op => op.value === value);
            unselectOption.selected = false;
            inputContainer.removeChild(item);
        });
        setValues();
        resetErrorMessages();
    });


    function init() {
        element = document.getElementById(el)
        createElements()
        initOptions()
        enableItemSelection()
        setValues(false)

        button.addEventListener('click', () => {
            if (drawer.classList.contains('hidden')) {
                initOptions()
                enableItemSelection()
                drawer.classList.remove('hidden')
                input.focus()
            } else {
                drawer.classList.add('hidden')
            }
        })

        input.addEventListener('keyup', (e) => {
            initOptions(e.target.value)
            enableItemSelection()
        })

        input.addEventListener('keydown', (e) => {
            if (e.key === 'Backspace' && !e.target.value && inputContainer.childElementCount > 1) {
                const child = body.children[inputContainer.childElementCount - 2].firstChild
                const option = options.find((op) => op.value === child.dataset.value)
                option.selected = false
                removeTag(child.dataset.value)
                setValues()
            }

        })

        window.addEventListener('click', (e) => {
            if (!customSelectContainer.contains(e.target)) {
                drawer.classList.add('hidden')
            }
        });

    }

    function createElements() {
        // Create custom elements
        options = getOptions();
        element.classList.add('hidden')

        // .multi-select-tag
        customSelectContainer = document.createElement('div')
        customSelectContainer.classList.add('mult-select-tag')

        // .container
        wrapper = document.createElement('div')
        wrapper.classList.add('wrapper')

        // body
        body = document.createElement('div')
        body.classList.add('body')
        if (customs.shadow) {
            body.classList.add('shadow')
        }
        if (customs.rounded) {
            body.classList.add('rounded')
        }

        // .input-container
        inputContainer = document.createElement('div')
        inputContainer.classList.add('input-container')

        // input
        input = document.createElement('input')
        input.classList.add('input')
        input.placeholder = `${customs.placeholder || 'Szukaj...'}`

        inputBody = document.createElement('inputBody')
        inputBody.classList.add('input-body')
        inputBody.append(input)

        body.append(inputContainer)

        // .btn-container
        btnContainer = document.createElement('div')
        btnContainer.classList.add('btn-container')

        // button
        button = document.createElement('button')
        button.type = 'button'
        btnContainer.append(button)

        const icon = document.createElement('i');
        icon.classList.add('fa', 'fa-solid', 'fa-chevron-down');
        button.append(icon)


        body.append(btnContainer)
        wrapper.append(body)

        drawer = document.createElement('div');
        drawer.classList.add(...['drawer', 'hidden'])
        if (customs.shadow) {
            drawer.classList.add('shadow')
        }
        if (customs.rounded) {
            drawer.classList.add('rounded')
        }
        drawer.append(inputBody)
        ul = document.createElement('ul');

        drawer.appendChild(ul)

        customSelectContainer.appendChild(wrapper)
        customSelectContainer.appendChild(drawer)

        // Place TailwindTagSelection after the element
        if (element.nextSibling) {
            element.parentNode.insertBefore(customSelectContainer, element.nextSibling)
        } else {
            element.parentNode.appendChild(customSelectContainer);
        }
    }

    function initOptions(val = null) {
        ul.innerHTML = ''
        for (let option of options) {
            if (option.selected) {
                !isTagSelected(option.value) && createTag(option)
            } else {
                const li = document.createElement('li')
                li.innerHTML = option.label
                li.dataset.value = option.value

                // For search
                if (val && option.label.toLowerCase().startsWith(val.toLowerCase())) {
                    ul.appendChild(li)
                } else if (!val) {
                    ul.appendChild(li)
                }
            }
        }
    }

    function createTag(option) {
        // Create and show selected item as tag
        const itemDiv = document.createElement('div');
        itemDiv.classList.add('item-container');

        const itemLabel = document.createElement('div');
        if(el==="categories"){
            itemLabel.classList.add("item-categories")
        }
        if(el==="platforms"){
            itemLabel.classList.add("item-platforms")
        }
        itemLabel.classList.add('item-label');
        itemLabel.innerHTML = option.label
        itemLabel.dataset.value = option.value

        let itemMainCategory;
        if (el === "categories") {
            itemMainCategory = document.createElement('i');
            itemMainCategory.classList.add('fa', 'fa-solid', 'fa-crown', "main-category");
        }
        const itemClose = document.createElement('i');

        itemClose.classList.add('fa', 'fa-times', 'item-close-icon');


        itemClose.addEventListener('click', (e) => {
            const unselectOption = options.find((op) => op.value === option.value)
            unselectOption.selected = false
            removeTag(option.value)
            initOptions()
            setValues()
        });
        const container = document.querySelector('.input-container');


        itemDiv.appendChild(itemLabel);
        // Add crown element if el is categories
        if (el === "categories") {
            itemDiv.appendChild(itemMainCategory);
            itemMainCategory.addEventListener('click', () => {
                setMainCategory(container, itemDiv);
                setValues();
            });
        }
        itemDiv.appendChild(itemClose);
        inputContainer.append(itemDiv);


    }

    function setMainCategory(container, itemDiv) {
        if (container.querySelectorAll('div.chosen').length < 1) {
            itemDiv.classList.add("chosen");
        } else {
            itemDiv.classList.remove("chosen");
        }
    }

    function enableItemSelection() {
        // Add click listener to the list items
        for (let li of ul.children) {
            li.addEventListener('click', (e) => {
                options.find((o) => o.value === e.target.dataset.value).selected = true
                input.value = null
                initOptions()
                setValues()
                input.focus()
            })
        }
    }

    function isTagSelected(val) {
        // If the item is already selected
        for (let child of inputContainer.children) {
            if (!child.classList.contains('input-body') && child.firstChild.dataset.value === val) {
                return true
            }
        }
        return false
    }

    function removeTag(val) {
        // Remove selected item
        for (let child of inputContainer.children) {
            if (!child.classList.contains('input-body') && child.firstChild.dataset.value === val) {
                inputContainer.removeChild(child)
            }
        }
    }

    function setValues(fireEvent = true) {

        // Update element final values
        let selected_values = []

        for (let i = 0; i < options.length; i++) {
            element.options[i].selected = options[i].selected
            if (options[i].selected) {
                selected_values.push({label: options[i].label, value: options[i].value})
            }
        }

        let mainCategory = document.querySelector('.chosen');
        if (mainCategory) {
            document.getElementById('mainCategory').value = mainCategory.textContent.valueOf();
        }

        if (fireEvent && customs.hasOwnProperty('onChange')) {
            customs.onChange(selected_values)
        }
        return selected_values;
    }
    customSelectContainer.addEventListener("click",function (){
        if (el === "categories") {
            validateCategories();
        }
        if (el === "platforms") {
            validateGamePlatforms();
        }

    });

    function getOptions() {
        // Map element options
        return [...element.options].map((op) => {
            return {
                value: op.value,
                label: op.label,
                selected: op.selected,
            }
        })
    }
}