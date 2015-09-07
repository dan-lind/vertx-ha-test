/**
 * Created by danlin on 2015-09-04.
 */
exports.handler = function(event, context) {
    context.succeed(event.hello);  // Echo back the event content
};

