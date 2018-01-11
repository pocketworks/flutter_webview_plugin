//
//  WebviewController.m
//  Pods
//
//  Created by Toufik Zitouni on 6/17/17.
//
//

#import "WebviewController.h"

@implementation WebviewController : UIViewController

- (void)viewWillTransitionToSize:(CGSize)size withTransitionCoordinator:(id<UIViewControllerTransitionCoordinator>)coordinator {
    [super viewWillTransitionToSize:size withTransitionCoordinator:coordinator];
    if (self.webview) {
        self.webview.frame = CGRectMake(self.webview.bounds.origin.x, self.webview.bounds.origin.y, size.width, size.height);
        [self.webview stringByEvaluatingJavaScriptFromString:[NSString stringWithFormat:@"window.resizeTo(%.2f,%.2f)", size.width, size.height]];
    }
}


@end
