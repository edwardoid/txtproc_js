var http = require('http');
var natural = require('natural');
var tokenizer = new natural.WordTokenizer();
var porter = natural.PorterStemmer;
var lancaster = natural.LancasterStemmer;
var levenshtine = natural.LevenshteinDistance;
var jaro_winkler = natural.JaroWinklerDistance;
var ngrams = natural.NGrams;
var stopwords_en = require('stopwords').english;

function isEmpty(ob){
   for(var i in ob){ return false;}
  return true;
}

http.createServer(function (req, res) {

	var doc = req.headers['doc'];
	if(doc == null) {
		res.writeHead(400, {'Content-Type': 'application/json'});
		res.end(JSON.stringify({'error' : 'parameter \"doc\" is not specified in headers'}));
		return;
	}

	// Self-configuring
	var include_original_doc =		req.headers['include_original_doc'] === 'true'
	var include_tokens =			req.headers['include_tokens'] === 'true'
	var exclude_stops = 			req.headers['exclude_stops'] === 'true';
	var include_non_stops = 		req.headers['include_non_stops'] === 'true';
	var run_porter = 				req.headers['run_porter'] === 'true';
	var run_lancaster = 			req.headers['run_lancaster'] === 'true';
	var calculate_levenshtine = 	req.headers['calculate_levenshtine'] === 'true';
	var calculate_jaro_winkler =	req.headers['calculate_jaro_winkler'] === 'true';
	var include_n_grams = 			new Number(req.headers['include_n_grams']);

	var lookup_for_stops = !exclude_stops || include_non_stops;
	var calculate_any_distance = run_porter && run_lancaster && (calculate_levenshtine || calculate_jaro_winkler);

	var tokens = tokenizer.tokenize(doc.toLowerCase());
	if(tokens == null)
		tokens = [];

	var porter_results = [];
	var lancaster_results = [];
	var stop_words_result = [];
	var non_stops_result = [];
	var levenshtine_results = [];
	var jaro_winkler_results = [];
	var wm_results = [];

	// Processing text
	tokens.forEach(function(t) {

		// Processing stop words
		if(lookup_for_stops) {
			if(stopwords_en.indexOf(t) > -1) {
				if(!exclude_stops)
					stop_words_result.push(t);
			}
			else {
				if(include_non_stops)
					non_stops_result.push(t);
			}
		}

		// Stemming
		var pr = "";
		var lr = "";
		if(run_porter) {
			pr = porter.stem(t);
			porter_results.push(pr);
		}
		if(run_lancaster) {
			lr = lancaster.stem(t);
			lancaster_results.push(lr)
		}

		// Caculating distances
		if(calculate_any_distance) {
			if(calculate_levenshtine)
				levenshtine_results.push(levenshtine(lr, pr));
			if(calculate_jaro_winkler)
				jaro_winkler_results.push(jaro_winkler(lr, pr));
		}
	});

	// Collecting output data
	var stems = {};
	var normalized = {}
	var distances = {}

	if(run_porter) {
		stems['porter'] = porter_results;
		normalized['porter'] = porter_results.join(' ');
	}
	if(run_lancaster) {
		stems['lancaster'] = lancaster_results;
		normalized['lancaster'] = lancaster_results.join(' ');
	}

	if(calculate_any_distance) {
		if(calculate_levenshtine)
			distances['levenshtine'] = levenshtine_results;
		if(calculate_jaro_winkler)
			distances['jaro_winkler'] = jaro_winkler_results;
	}

	// Generating responce
	res.writeHead(200, {'Content-Type': 'application/json'});

	var resp_headers = {};
	if(include_original_doc)
		resp_headers['original'] = doc;

	if(include_tokens)
		resp_headers['tokens'] = tokens;

	if(!exclude_stops)
		resp_headers['stops'] = stop_words_result;

	if(include_non_stops)
		resp_headers['non_stops'] = non_stops_result;

	if(!isEmpty(stems) || !isEmpty(normalized) || !isEmpty(distances)) {
		resp_headers['stemmed'] = {}
		if(!isEmpty(stems))
			resp_headers['stemmed'] = stems;
		if(!isEmpty(normalized))
			resp_headers['normalized'] = normalized;

		if(!isEmpty(distances))
			resp_headers['distances'] = distances;
	}

	if(include_n_grams > 0)
		resp_headers['ngrams'] = ngrams.ngrams(tokens, include_n_grams);

	// Done!
	res.end(JSON.stringify(resp_headers, null, 2));
}).listen(8080);

console.log('Server running on port 8080.');	