import {
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    getParameterByName
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayPlayedCharacters();
});

/**
 * Fetches all users and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayPlayedCharacters() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayPlayedCharacters(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/playedCharacters', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display users.
 *
 * @param {{characters: {id: number, name:string}[]}} characters
 */
function displayPlayedCharacters(playedCharacters) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    playedCharacters.playedCharacters.forEach(playedCharacter => {
        tableBody.appendChild(createTableRow(playedCharacter));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {{id: number, name: string}} character
 * @returns {HTMLTableRowElement}
 */
function createTableRow(playedCharacter) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(playedCharacter.name));
    tr.appendChild(createLinkCell('edit', '../played_characters_edit/played_characters_edit.html?playedCharacter=' + playedCharacter.id));
    tr.appendChild(createButtonCell('delete', () => deleteCharacter(playedCharacter.id)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {number} character to be deleted
 */
function deleteCharacter(playedCharacter) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayPlayedCharacters();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/playedCharacters/' + playedCharacter, true);
    xhttp.send();
}
