//-------------------------------------------------------------------------------------------
//------------------------------------ Global section  --------------------------------------
//-------------------------------------------------------------------------------------------
let roles

//-------------------------------------------------------------------------------------------
async function initialisation(url) {
    const response = await fetch(url)
    const data = await response.json()
    if (data.length > 0) {
        let template = ``
        data.forEach(u => {
            let roles = ''
            u.roles.forEach(r => {
                roles += r.authority.substring(5) + ' '
            })

            template += `
                <tr id="tr${u.id}">
                    <td class="text-wrap">${u.id}</td>
                    <td class="text-wrap">${u.firstName}</td>
                    <td class="text-wrap">${u.lastName}</td>
                    <td class="text-wrap">${u.email}</td>
                    <td class="text-wrap">${roles}</td>
                    <td class="text-wrap">
                        <button type="button" class="btn btn-sm btn-info" id ="editBtn${u.id}">
                        Edit</button></td>
                    <td class="text-wrap">
                        <button type="button" class="btn btn-sm btn-danger" id ="deleteBtn${u.id}">
                        Delete</button></td>
                </tr>`
        })
        document.getElementById('tbody').innerHTML = template
        console.log(data)
        console.log('Table fetched!')
    }
}

async function fetchPrincipal(url) {
    const response = await fetch(url)
    const data = await response.json()
    let email,
        roles = ''

    if (data != null) {
        data.roles.forEach(r => {
            roles += r.authority.substring(5) + ' '
        })
        email = data.email

        let template = ``
        template += `
            <tr id="tr${data.id}">
                <td class="text-wrap">${data.id}</td>
                <td class="text-wrap">${data.firstName}</td>
                <td class="text-wrap">${data.lastName}</td>
                <td class="text-wrap">${email}</td>
                <td class="text-wrap">${roles}</td>
            </tr>`
        document.getElementById('principalBody').innerHTML = template
        document.getElementById('titlePrincipal').innerHTML = `<b> ${email} </b>  with roles:  ${roles}`
    }
}

async function fetchRoles(url) {
    const response = await fetch(url)
    const data = await response.json()
    let allRoles = []

    if (data.length > 0) {
        data.forEach(r => {
            allRoles.push(r.authority.substring(5))
        })
        console.log('Roles fetched!')
    }

    console.log(allRoles)
    roles = allRoles
    return allRoles
}

//-------------------------------------------------------------------------------------------
function bindButtons(roles) {
    $("[id^='deleteBtn']").click(function () {
        const row = $(this).parents('tr')
        $('#onDeleteId').attr('value', row.find('td:eq(0)').text())
        $('#onDeleteFirstName').attr('value', row.find('td:eq(1)').text())
        $('#onDeleteLastName').attr('value', row.find('td:eq(2)').text())
        $('#onDeleteEmail').attr('value', row.find('td:eq(3)').text())
        $('#onDeleteRoles').attr('value', row.find('td:eq(4)').text())  // ACHTUNG!!!!
        $('#modalDelete').modal('show')
        console.log('...deleteBtn click-bound function fully executed.')
    })
    $('#submitDeleteBtn').click(function () {
        const id = $('#onDeleteId').attr('value')
        fetch('/admin/users/' + id, {
            method: 'DELETE'
        })
            .then(res => res.text())
            .then(() => $('#tr' + id).remove())
        $('#modalDelete').modal('hide')
        console.log('...submitDeleteBtn click-bound function fully executed.')
    })

    $("[id^='editBtn']").click(function () {
        const row = $(this).parents('tr')
        // const row = document.getElementById($(this)).parentElement.closest('tr')
        document.getElementById("onEditId").value = row.find('td:eq(0)').text()
        document.getElementById("onEditFirstName").value = row.find('td:eq(1)').text()
        document.getElementById("onEditLastName").value = row.find('td:eq(2)').text()
        document.getElementById("onEditEmail").value = row.find('td:eq(3)').text()
        document.getElementById("onEditPassword").value = ''

        let rolesAsOptionsList = ''
        // console.log(roles.length)
        console.log(roles)
        // for (let i = 0; i < 2; i++) {
        //     rolesAsOptionsList += '<option>' + roles[i] + '</option>'
        // }
        // roles.forEach((elem) => {
        //     rolesAsOptionsList += '<option>' + elem + '</option>'
        // })
        rolesAsOptionsList = '<option>USER</option><option>ADMIN</option>'
        document.getElementById('onEditRoles').innerHTML = rolesAsOptionsList       // ACHTUNG!!!!

        $('#modalEdit').modal('show')
        console.log('...editBtn click-bound function fully executed.')
    })
    $('#submitEditBtn').click(function () {
        const id = document.getElementById("onEditId").value
        const user = {
            id: id,
            firstName: document.getElementById("onEditFirstName").value,
            lastName: document.getElementById("onEditLastName").value,
            email: document.getElementById("onEditEmail").value,
            password: document.getElementById("onEditPassword").value,
            roles: {
                id: 2,
                authority: 'ROLE_USER'
            }                                                           // ACHTUNG!!!!!!!
        }
        console.log(user)

        const requestCfg = {
            method: 'PUT',
            body: user,
            headers: {
                'content-type': 'application/json'
            }
        }

        console.log(requestCfg)
        fetch('http://localhost:8080/admin/users/' + id, requestCfg)
            .then(res => res.json())
            .then(data => console.log(data))
        $('#modalEdit').modal('hide')
        console.log('...submitEditBtn click-bound function fully executed.')
    })

    $('#addNewUser').click(function () {
        const newUser = {
            firstName: document.getElementById("onNewFirstName").value,
            lastName: document.getElementById("onNewLastName").value,
            email: document.getElementById("onNewEmail").value,
            password: document.getElementById("onNewPassword").value,
            roles: {
                id: 2,
                authority: 'ROLE_USER'
            }                                                           // ACHTUNG!!!!!!!
        }

        console.log(newUser)
        fetch('http://localhost:8080/admin/users', {
            method: 'POST',
            body: newUser,
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(res => res.json())
            .then(data => console.log(data))

        // document.getElementById("onNewFirstName").value = ''
        // document.getElementById("onNewLastName").value = ''
        // document.getElementById("onNewEmail").value = ''
        // document.getElementById("onNewPassword").value = ''

        console.log('...newBtn click-bound function fully executed.')
    })
}

//-------------------------------------------------------------------------------------------
//-------------------------------------- Main section  --------------------------------------
//-------------------------------------------------------------------------------------------
initialisation('http://localhost:8080/odmen')
    .then(() => {
        fetchPrincipal('http://localhost:8080/user')
            .then(() => {
                console.log('principal info uploaded')
            })
    })
    .then(() => {
        fetchRoles('http://localhost:8080/roles')
            .then(() => {
                console.log('roles fetched')
            })
    })
    .then(() => {
        console.log('vvvvvv Roles vvvvvvv')
        console.log(roles)
        console.log('^^^^^^^ Roles ^^^^^^^')
        console.log('role is ' + typeof roles)
        bindButtons(roles)
    })
    .then(() => {
        console.log('binded!')
    })

$(document).ready(function () {
    console.log('Loaded!')

})





