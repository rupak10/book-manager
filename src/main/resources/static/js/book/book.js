$('.btnBookDel').click(function() {
	let bookJson = $(this).attr('data-book');
	const book = convertStringToJsObject(bookJson);
	//console.log(book);

	populateModal(book);
	
	const bookModal = new bootstrap.Modal(document.getElementById("bookModal"), {'backdrop' :'static'});
	bookModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("BookDTO(", "").replace(")", "");
	// Step 2: Split the string into individual key-value pairs
	const keyValuePairs = trimmedData.split(", ");
	// Step 3: Create the JavaScript object using these key-value pairs
	const jsObject = {};
	keyValuePairs.forEach(pair => {
		const [key, value] = pair.split("=");
		jsObject[key] = value === "null" ? null : value;
	});
	return jsObject;
};


const populateModal = (book) => {
	
	let deleteUrl = '/books/delete/' + book.id;
		
	var $DeleteLink = $('#delBookBtn');
    $DeleteLink.attr('href', deleteUrl);
};


	