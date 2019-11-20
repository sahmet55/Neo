$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/GoogleSearch.feature");
formatter.feature({
  "name": "Google Search",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Search by typing",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@jenkins"
    }
  ]
});
formatter.before({
  "status": "skipped"
});
formatter.step({
  "name": "I navigated to the Google",
  "keyword": "Given "
});
formatter.match({
  "location": "GoogleSearchSteps.i_navigated_to_the_Google()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I type search item",
  "keyword": "When "
});
formatter.match({
  "location": "GoogleSearchSteps.i_type_search_item()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I click on google search button",
  "keyword": "And "
});
formatter.match({
  "location": "GoogleSearchSteps.i_click_on_google_search_button()"
});
formatter.result({
  "status": "skipped"
});
formatter.step({
  "name": "I see search results are displayed",
  "keyword": "Then "
});
formatter.match({
  "location": "GoogleSearchSteps.i_see_search_results_are_displayed()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "status": "skipped"
});
});