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
        self.webview.frame = CGRectMake(0.0, 0.0, size.width, size.height);
    }
}


@end
