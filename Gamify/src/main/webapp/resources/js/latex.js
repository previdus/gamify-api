function replaceSpaceByLatexSpace(text) {

	if (text.indexOf("\[") > 0 && text.indexOf("\]") > 0
			&& text.indexOf(' ') > 0) {
		var replacedText = text.replace(/ /g, "\\hspace{0.1cm}");
		return replacedText;
	}
	return text;
}

function replaceNewLineByLatexVerticalSpace(text) {
	
	if (text.indexOf("\[") > 0 && text.indexOf("\]") > 0
			&& text.indexOf('\n') > 0) {
		var replacedText = text.replace(/\n/g, "\\\\");
		return replacedText;
	}

	return text;
}

function sanitizeForLatex(text){
	text = replaceSpaceByLatexSpace(text);
	text = replaceNewLineByLatexVerticalSpace(text);
	return text;
}